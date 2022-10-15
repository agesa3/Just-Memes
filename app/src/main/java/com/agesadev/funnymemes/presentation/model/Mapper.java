package com.agesadev.funnymemes.presentation.model;

import com.agesadev.funnymemes.domain.model.MemeDomain;

import java.util.ArrayList;
import java.util.List;

import omari.hamza.storyview.model.MyStory;

public class Mapper {
    public static List<MyStory> getMemeUrls(List<MemePresentation> memeDomainList) {
        List<MyStory> myStories = new ArrayList<>();
        for (MemePresentation memePresentation : memeDomainList) {
            myStories.add(new MyStory(memePresentation.getImageurl()));
        }
        return myStories;
    }
}