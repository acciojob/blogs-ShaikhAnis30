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
        Image image = new Image(description, dimensions);

        Blog blog = blogRepository2.findById(blogId).get();

        List<Image> imageList = blog.getImageList();
        imageList.add(image);
        blog.setImageList(imageList);

        image.setBlog(blog);  //check if error comes

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
        int totalScreenDimension = Integer.parseInt(screenDimensions.substring(0,1)) *
                                   Integer.parseInt(screenDimensions.substring(2));

        int currentImageDimension = Integer.parseInt(dimensionOfImage.substring(0,1)) *
                                    Integer.parseInt(dimensionOfImage.substring(2));

//        int totalScreenDimension = Integer.parseInt(screenDimensions);
//        int currentImageDimension = Integer.parseInt(dimensionOfImage);

        int imageCount = 0;
        int sum = 0;
        for (int i = 0; i < totalScreenDimension/2; i++) {
            sum += currentImageDimension;

            if(sum <= totalScreenDimension) imageCount++;
            else break;
        }

        return imageCount;
    }
}
