package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Image image = new Image();
        //set attributes
        image.setDescription(description);
        image.setDimensions(dimensions);
        //image created

        Blog blog = blogRepository2.findById(blogId).get();
        image.setBlog(blog);

        List<Image> imageList = blog.getImageList();
        imageList.add(image);
        blog.setImageList(imageList);

        blogRepository2.save(blog); //by cascading effect image will be automatically saved

        return image;
    }

    public void deleteImage(Integer id) {
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions)  {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image = imageRepository2.findById(id).get();
        String dimensionOfImage = image.getDimensions();//is dimension wale images I have to count

        //dimension = Length x Breadth
        String[] totalScreen = screenDimensions.split("X");
        String[] givenImage = dimensionOfImage.split("X");
        int lengthByLength = Integer.parseInt(totalScreen[0]) / Integer.parseInt(givenImage[0]);

        int breadthByBreadth = Integer.parseInt(totalScreen[1]) / Integer.parseInt(givenImage[1]);

        return lengthByLength * breadthByBreadth;

        //dimension = Length x Breadth
        //this will fail if length and breadth have digits more than 1
        /**int lengthByLength = Integer.parseInt(screenDimensions.substring(0,1)) *
                                   Integer.parseInt(dimensionOfImage.substring(0,1));

        int breadthBybBreadth = Integer.parseInt(screenDimensions.substring(2)) *
                                    Integer.parseInt(dimensionOfImage.substring(2));

        int imageCount = lengthByLength + breadthBybBreadth;

        return imageCount;
         **/


    }
}
