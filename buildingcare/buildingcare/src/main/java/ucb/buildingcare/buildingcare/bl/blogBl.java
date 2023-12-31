package ucb.buildingcare.buildingcare.bl;

import java.util.Date;
import java.sql.Time;


import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ucb.buildingcare.buildingcare.dto.BuildingcareResponse;
import ucb.buildingcare.buildingcare.dto.PostRequest;
import ucb.buildingcare.buildingcare.dto.PostResponse;
import ucb.buildingcare.buildingcare.entity.Post;
import ucb.buildingcare.buildingcare.entity.TypePost;
import ucb.buildingcare.buildingcare.repository.PostRepository;
import ucb.buildingcare.buildingcare.repository.UserRepository;
import ucb.buildingcare.buildingcare.util.BuildingcareException;
import ucb.buildingcare.buildingcare.repository.TypePostRepository;

@Service
public class BlogBl {
    //Esta clase es la que se encarga de la logica sobre el blog de publicaciones
    //Requiere de las tablas:
    //Post
    //User
    //TypePost

    Logger LOGGER = LoggerFactory.getLogger(BlogBl.class);
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TypePostRepository typePostRepository;

    public BlogBl(PostRepository postRepository, UserRepository userRepository, TypePostRepository typePostRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.typePostRepository = typePostRepository;
    }

    public BuildingcareResponse ListAllPosts() {
        LOGGER.info("blogBl - ListAllPosts");
        List<Post> posts = postRepository.findAll();
        LOGGER.info("el tamano de posts List<Post> es: "+ posts.size());
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            LOGGER.info("en el for de List<Post> posts: "+ post.toString());
            postResponses.add(new PostResponse(post));
        }
        LOGGER.info("retornando new BuildingcareResponse(postResponses): "+ new BuildingcareResponse(postResponses).toString());
        return new BuildingcareResponse(postResponses);
    }

    public PostResponse getPostById(Integer id) throws BuildingcareException {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            return new PostResponse(post);
        } else {
            throw new BuildingcareException("No se encontró la publicación");
        }
    }

    public BuildingcareResponse getPostsByStatus(String status) throws BuildingcareException {
        LOGGER.info("blogBl - getPostsByStatus: "+status);
        List<Post> posts = postRepository.findAll();
        LOGGER.info("el tamano de posts List<Post> es: "+ posts.size());
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            LOGGER.info("en el for de List<Post> posts: "+ post.toString());
            if(post.getState().equalsIgnoreCase(status)) {
                postResponses.add(new PostResponse(post));
            }
        }
        if (postResponses.size() == 0) {
            throw new BuildingcareException("No se encontraron publicaciones con el estado "+status);
        }
        LOGGER.info("retornando new BuildingcareResponse(postResponses): "+ new BuildingcareResponse(postResponses).toString());
        return new BuildingcareResponse(postResponses);
    }

    public PostResponse createPost(PostRequest postRequest, Integer token) {
        Post post = new Post();
        //insert with current date
        post.setDate(new Date());
        post.setHour(new Time(System.currentTimeMillis()));
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setState(postRequest.getState());
        post.setIdUser(userRepository.findById(token).orElse(null));
        post.setIdTypePost(typePostRepository.findById(postRequest.getIdTypePost()).orElse(null));

        //If post has a parent post
        // post.setIdPostRequest(postRepository.findById(postRequest.getIdPostRequest()).orElse(null));
        if(postRequest.getIdPostRequest() != null) {
            post.setIdPostRequest(postRepository.findById(postRequest.getIdPostRequest()).orElse(null));
        }

        
        LOGGER.info("blogBl - creating post: "+ post.toString());
        // Save the post object to the database
        try {
                postRepository.save(post);
            } catch (Exception e) {
                LOGGER.error("No se pudo guardar el elemento: {}", e);
            }
        return new PostResponse(post);
    }

    public PostResponse deletePost(Integer id) throws BuildingcareException {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            postRepository.delete(post);
            return new PostResponse(post);
        } else {
            throw new BuildingcareException("No se encontró la publicación");
        }
    }

    public PostResponse markPostAsDone(Integer id, Integer token) throws BuildingcareException {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setState("Done");
            try {
                postRepository.save(post);
            } catch (Exception e) {
                LOGGER.error("No se pudo guardar el elemento: {}", e);
            }
            return new PostResponse(post);
        } else {
            throw new BuildingcareException("No se encontró la publicación");
        }
    }
    
    public PostResponse markPostAsUrgent(Integer id, Integer token) throws BuildingcareException {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setState("Urgent");
            try {
                postRepository.save(post);
            } catch (Exception e) {
                LOGGER.error("No se pudo guardar el elemento: {}", e);
            }
            return new PostResponse(post);
        } else {
            throw new BuildingcareException("No se encontró la publicación");
        }
    }

    public PostResponse updatePost(Integer id, PostRequest postRequest, Integer token) throws BuildingcareException {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {

            if (postRequest.getTitle() != null) post.setTitle(postRequest.getTitle());
            if (postRequest.getContent() != null) post.setContent(postRequest.getContent());
            if (postRequest.getState() != null) post.setState(postRequest.getState());
            if (postRequest.getIdUser() != null) post.setIdUser(userRepository.findById(token).orElse(null));
            if (postRequest.getIdTypePost() != null) post.setIdTypePost(typePostRepository.findById(postRequest.getIdTypePost()).orElse(null));
            
            try {
                postRepository.save(post);
            } catch (Exception e) {
                LOGGER.error("No se pudo guardar el elemento: {}", e);
            }
            
            return new PostResponse(post);
        } else {
            throw new BuildingcareException("No se encontró la publicación");
        }
    }

    public BuildingcareResponse listAllPostTypes() throws BuildingcareException{
        LOGGER.info("blogBl - listAllPostTypes");
        List<TypePost> typePosts = typePostRepository.findAll();
        LOGGER.info("el tamano de typePosts List<TypePost> es: "+ typePosts.size());
        if (typePosts.size() == 0) {
            LOGGER.error("No se encontraron tipos de publicaciones");
            throw new BuildingcareException("No se encontraron tipos de publicaciones");
        }
        BuildingcareResponse buildingcareResponse = new BuildingcareResponse(typePosts);
        LOGGER.info("retornando new BuildingcareResponse(typePosts): "+ buildingcareResponse.toString());
        return buildingcareResponse;
    }
    
}
