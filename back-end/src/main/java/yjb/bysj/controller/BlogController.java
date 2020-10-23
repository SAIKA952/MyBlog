package yjb.bysj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yjb.bysj.common_utils.JwtUtils;
import yjb.bysj.common_utils.Res;
import yjb.bysj.entity.Account;
import yjb.bysj.entity.Blog;
import yjb.bysj.entity.util.Index;
import yjb.bysj.service.AccountService;
import yjb.bysj.service.BlogService;
import yjb.bysj.service.CommentService;
import yjb.bysj.service.LikedService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController // 相当于@Controller和@ResponseBody两个注解
@RequestMapping("/blog")
@CrossOrigin // 解决跨域问题
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private LikedService likedService;

    // 查找所有博客
    @GetMapping("/searchAll")
    public Res searchAll() {
        List<Blog> blogs = blogService.searchAll(); // 查找所有博客
        List<Index> indexList = new ArrayList<>(); // 存放首页显示数据

        for(int i = 0; i < blogs.size(); i++) {
            Index index = new Index();
            index.setAuthorId(blogs.get(i).getAuthorId());

            Account account = accountService.getAccountInfoById(blogs.get(i).getAuthorId()); // 获取作者信息
            index.setAuthorName(account.getUsername());
            index.setAuthorAvatar(account.getAvatar());

            // 获取评论数
            Integer commentsCount = commentService.getCommentsCountByBlogId(blogs.get(i).getId());
            index.setCommentsCount(commentsCount);

            // 获取点赞数
            Integer likedCount = likedService.getLikedCountByBlogId(blogs.get(i).getId());
            index.setLikedCount(likedCount);

            // 获取收藏数
            Integer collectCount = likedService.getCollectCountByBlogId(blogs.get(i).getId());
            index.setCollectCount(collectCount);

            // 截取字符串
            String content = blogs.get(i).getContent();
            if(blogs.get(i).getContent().length() >= 100) {
                content = content.trim().substring(0, 100) + "...";
            }
            index.setBlogContent(content);

            index.setViews(blogs.get(i).getViews());
            index.setBlogTitle(blogs.get(i).getTitle());
            index.setBlogId(blogs.get(i).getId());
            index.setCreateOn(blogs.get(i).getCreateOn());

            indexList.add(index);
        }

        return Res.ok().data("indexList", indexList);
    }

    // 博客提交/保存
    @PostMapping("/submit")
    public Res submit(@RequestBody Blog blog, HttpServletRequest request) {
        // 获取博客的状态 如果状态为0，表示修改草稿，并没有提交；如果状态为1或者为null，表示为修改博客或者为新博客提交
//        Integer blogStatus = blog.getStatus();

        if (null == blog.getId()) { // 新博客的发表或保存，博客状态由前端设置的status决定
            // 从token中获取用户的id，存到blog中的authorId字段
            String id = JwtUtils.getMemberIdByJwtToken(request);

            blog.setComments(0); // 评论数
            blog.setLikes(0); // 点赞数
            blog.setAuthorId(Integer.parseInt(id)); // 作者id
            blog.setViews(0); // 访问量
            blog.setVersion(0); // 版本号
            blog.setType(0); // 博客类型
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间
            blog.setCreateOn(timestamp); // 创建时间
            blog.setUpdateOn(timestamp); // 修改时间

            Res res = blogService.submitBlog(blog);
            return res;
        } else { // 修改已发表的博客或者是草稿箱里的博客
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间
            blog.setUpdateOn(timestamp); // 修改时间

            blogService.updateBlog(blog);
            return Res.ok();
        }
    }

    // 根据博客id查询博客信息，顺便获取作者信息
    @GetMapping("/getBlogInfoById/{blogoId}")
    public Res getBlogInfoById(@PathVariable Integer blogoId) {
        blogService.addViews(blogoId);
        Blog blog = blogService.getBlogInfoById(blogoId);

        // 根据作者id获取作者的信息
        Integer authorId = blog.getAuthorId();
        Account authorInfo = accountService.getAccountInfoById(authorId);

        // 根据博客id获取该博客的评论数
        Integer count = commentService.getCommentsCountByBlogId(blogoId);

        return Res.ok().data("blog", blog).data("authorInfo", authorInfo).data("commentCount", count);
    }

    // 查找内容
    @GetMapping("/search/{searchContent}")
    public Res searchContent(@PathVariable String searchContent){

        List<Blog> blogs = blogService.searchContent(searchContent);
        List<Index> indexList = new ArrayList<>();

        for(int i = 0; i < blogs.size(); i++) {
            Index index = new Index();
            index.setAuthorId(blogs.get(i).getAuthorId());

            Account account = accountService.getAccountInfoById(blogs.get(i).getAuthorId());
            index.setAuthorName(account.getUsername());
            index.setAuthorAvatar(account.getAvatar());

            // 截取字符串
            String content = blogs.get(i).getContent();
            if(content.length() >= 80) {
                content = content.trim().substring(0, 80) + "...";
            }
            index.setBlogContent(content);

            index.setViews(blogs.get(i).getViews());
            index.setBlogTitle(blogs.get(i).getTitle());
            index.setBlogId(blogs.get(i).getId());
            index.setCreateOn(blogs.get(i).getCreateOn());

            indexList.add(index);
        }

        return Res.ok().data("searchRes", indexList);
    }

    // 根据作者id查询作者的所有博客（用于显示该作者的其他博客）
    @PostMapping("/getBlogsByAuthorId")
    public Res getBlogsByAuthorId(@RequestBody Blog blog) {
        Integer id = blog.getAuthorId();
        Integer currentBlogId = blog.getId();
        List<Blog> list = blogService.getBlogsByAuthorId(id, currentBlogId);
        if (list.size() > 5) {
            list = list.subList(0, 5); // 只截取浏览量最高的五篇博客
        }

        // 截取博客标题
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().length() > 20) {
                list.get(i).setTitle(list.get(i).getTitle().substring(0, 20) + "...");
            }
        }

        return Res.ok().data("list", list).data("blogCount", list.size());
    }

    // 编辑博客时数据回显
    @GetMapping("/editBlog/{id}")
    public Res editBlog(@PathVariable Integer id) {
        Blog blogInfo = blogService.getBlogInfoById(id);

        return Res.ok().data("blogInfo", blogInfo);
    }

    // 修改博客
    @PostMapping("/updateBlog")
    public Res updateBlog(@RequestBody Blog blog) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // 获取当前时间
        blog.setUpdateOn(timestamp);
//        blog.setStatus(1);

        blogService.updateBlog(blog);

        return Res.ok();
    }

    // 删除博客，放入回收箱
    @PostMapping("/deleteBlog")
    public Res deleteBlog(@RequestBody Blog blog) {
        // 根据博客id删除博客
        blogService.deleteBlog(blog.getId());

        return Res.ok();
    }

    // 根据用户id查询用户的所有博客
    @GetMapping("/getAllBlogsByUserId/{userId}")
    public Res getAllBlogsByUserId(@PathVariable Integer userId) {
        List<Blog> blogList = blogService.getAllBlogsByUserId(userId);
        List<Index> indexList = new ArrayList<>();
        int count = blogList.size();

        for (int i = 0; i < blogList.size(); i++) {
            Index index = new Index();
            Blog blog = blogList.get(i);

            // 截取内容前80个字
            String content = blog.getContent();
            if (content.length() > 80) {
                content = content.substring(0, 80) + "...";
            }
            index.setBlogContent(content);

            // 截取标题前40个字
            String title = blog.getTitle();
            if (title.length() > 40) {
                title = title.substring(0, 40) + "...";
            }
            index.setBlogTitle(title);

            // 获取评论数
            Integer commentsCount = commentService.getCommentsCountByBlogId(blog.getId());
            index.setCommentsCount(commentsCount);

            // 获取点赞数
            Integer likedCount = likedService.getLikedCountByBlogId(blog.getId());
            index.setLikedCount(likedCount);

            // 获取收藏数
            Integer collectCount = likedService.getCollectCountByBlogId(blog.getId());
            index.setCollectCount(collectCount);

            index.setBlogId(blog.getId());
            index.setViews(blog.getViews());
            index.setCreateOn(blog.getCreateOn());

            indexList.add(index);
        }

        return Res.ok().data("blogList", indexList).data("blogCount", count);
    }

    // 根据用户id查询该用户草稿箱里的博客
    @GetMapping("/getDraftByUserId/{userId}")
    public Res getDraftByUserId(@PathVariable Integer userId) {
        List<Blog> list = blogService.getDraftByUserId(userId);

        return Res.ok().data("draftList", list);
    }
}
