package yjb.bysj.entity.util;

import lombok.Data;

@Data
public class FollowInfo {

    private Integer userId;

    private Integer followId;

    private String username;

    private String avatar;

    private Integer blogCount;

    private Integer likedCount;

    private Integer collectCount;

    private Integer fansCount;

    private Integer followCount;

}
