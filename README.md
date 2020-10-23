# MyBlog
基于SpringBoot+MyBatis+Vue的前后端分离的在线博客项目（Markdown编辑器）

主要功能：注册、登录账号；编写、浏览、评论博客；可以对博客进行点赞、收藏，可以看到其他用户编写的博客，以及关注其他用户

## back-end
后端使用SpringBoot+MyBatis，使用阿里云oss对博客中的图片以及用户头像进行上传

## front-end
前端使用nuxt.js框架，使用element-ui组件设计样式，使用mavon-editor组件支持Markdown编辑器

## 数据库
使用了6张表
account：负责保存用户的一些信息，用户名、密码、手机号等基本信息
blog：负责保存博客的信息，包括内容、标题、作者id等信息
comment：负责保存博客的评论信息
collect：负责保存收藏博客的信息
follow：负责保存关注用户的信息
liked：负责保存博客点赞


Powered by YJB
