package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    UserRepository userRepository1;

    public Blog createAndReturnBlog(Integer userId, String title, String content) {
        //create a blog at the current time
        Blog blog = new Blog();
        //set attributes
        blog.setTitle(title);
        blog.setContent(content);
        blog.setPubDate(new Date()); //today's date

        User user = userRepository1.findById(userId).get();
        //between blog to user
        List<Blog> blogList = user.getBlogList();
        blogList.add(blog);
        user.setBlogList(blogList);

        //foreign key attribute
        blog.setUser(user); //is user ka hai ye newly created blog --> Linked

        userRepository1.save(user); //by cascading effect blog will be automatically saved

        return blog;
    }

    public void deleteBlog(int blogId) {
        //delete blog and corresponding images
        blogRepository1.deleteById(blogId);
    }
}
