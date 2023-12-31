package ucb.buildingcare.buildingcare.dto;

import ucb.buildingcare.buildingcare.entity.Post;

public class PostResponse {
    //Esta clase es la que se encarga de enviar la respuesta sobre post al front end

    private Integer id;
    private String postDateAndHour;
    private String postTitle;
    private String postContent;
    private String postState;
    private String postUser;
    private String postType;
    private Integer postRequest;

    public PostResponse() {
    }

    public PostResponse(Post post) {
        this.id = post.getId();
        this.postDateAndHour = post.getDate()+" "+post.getHour();
        this.postTitle = post.getTitle();
        this.postContent = post.getContent();
        this.postState = post.getState();
        this.postUser = post.getIdUser().getUsename();
        this.postType = post.getIdTypePost().getCategory();
        // this.postRequest = post.getIdPostRequest().getContent();
        if(post.getIdPostRequest() != null) {
            this.postRequest = post.getIdPostRequest().getId();
        }
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idPost) {
        this.id = idPost;
    }

    public String getPostDateAndHour() {
        return postDateAndHour;
    }

    public void setPostDateAndHour(String postDateAndHour) {
        this.postDateAndHour = postDateAndHour;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostState() {
        return postState;
    }

    public void setPostState(String postState) {
        this.postState = postState;
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getPostRequest() {
        return postRequest;
    }

    public void setPostRequest(Integer postRequest) {
        this.postRequest = postRequest;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "PostResponse{" +
                "postDateAndHour='" + postDateAndHour + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postState='" + postState + '\'' +
                ", postUser='" + postUser + '\'' +
                ", postType='" + postType + '\'' +
                ", postRequest='" + postRequest + '\'' +
                '}';
    }
}
