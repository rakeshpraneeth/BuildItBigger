package com.krp.jokesprovider;

import java.util.ArrayList;
import java.util.Random;

public class JokesGenerator {

    private ArrayList<String> jokesList = new ArrayList();
    Random random = new Random();
    int listSize;

    public JokesGenerator(){
       createJokes();
       listSize = jokesList.size();
    }

    public String getJoke(){

        int index = random.nextInt(listSize);
        return jokesList.get(index);
    }


    private void createJokes(){
        jokesList.add("I can’t believe I got fired from the calendar factory. All I did was take a day off.");
        jokesList.add("An extensive government study has revealed that the leading cause of cancer in laboratory rats is scientists.");
        jokesList.add("I gave up jogging for health reasons. My thighs kept rubbing together and setting my pantyhose on fire.");
        jokesList.add("Q: Why shouldn’t you fall in love with a pastry chef? A: He’ll dessert you.");
        jokesList.add("I tell people I’m on a low-carb diet. But in reality, I just eat pasta while lying on the floor.");
        jokesList.add("Q: How are relationships a lot like algebra? A: Sometimes you look at your X and wonder Y.");
        jokesList.add("Girlfriend: “Am I pretty or ugly?” Boyfriend: “You’re both.” Girlfriend: “What do you mean?” Boyfriend: “You’re pretty ugly.”");
        jokesList.add("Q: Is Google male or female? A: Female, because it doesn’t let you finish a sentence before making a suggestion.");
    }

}
