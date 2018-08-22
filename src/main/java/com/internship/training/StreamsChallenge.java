package com.internship.training;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StreamsChallenge {
    
    private List<Series> series;

    public void executeChallenge() {
        getEpisodesTotalCount();
        getAverageEpisodesCount();
        getMaxEpisodeCount();
        getBest10SeriesByRating();
        getAllGenres();
        getSeriesByStudioShaft();
        getMostEpisodesSeries();
        getBestStudio();
        getBestGenre();
        getWorstGenre();
        getTop5MostCommmonEpisodeCount();
        getAverageRatingOfCommedySeries();
        getMostCommonGenreWhereSugitaTomokazuActs();
        getBestActor();
        getBestShounenStudio();
    }

    public StreamsChallenge(){
        init();
    }

    private void init(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("series.json").getFile());
            List<Series> importedSeries = mapper.readValue(file,new TypeReference<List<Series>>(){});
            this.series = importedSeries.stream().filter(x->x.getRating()>0).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getEpisodesTotalCount(){
        System.out.println("------------------------------------------------------------");
        long count = 0;
        //TODO add all episodes of all series, put the result in count
        List<Integer>episodes =this.series.stream().map(s->{return s.getEpisodes();}).collect(Collectors.toList());
        count=episodes.stream().mapToInt(i->i.intValue()).sum();
        //System.out.println(counts);
        System.out.println(String.format("Total episodes: %d",count));
        System.out.println("------------------------------------------------------------");
    }

    private void getAverageEpisodesCount(){
        System.out.println("------------------------------------------------------------");
        double average = 0;
        //TODO get the average number of episodes, put the result in average
        average=this.series.stream().mapToDouble(i->i.getEpisodes()).average().getAsDouble();

        System.out.println(String.format("Average number of episodes: %f",average));
        System.out.println("------------------------------------------------------------");
    }

    private void getMaxEpisodeCount(){
        System.out.println("------------------------------------------------------------");
        int count = 0;
        //TODO get the maximun number of episodes, put the result in count
        count =this.series.stream().map(s->{return s.getEpisodes();}).collect(Collectors.toList()).stream().mapToInt(i->i.intValue()).max().getAsInt();

        System.out.println(String.format("Max number of episodes: %d",count));
        System.out.println("------------------------------------------------------------");

    }

    private void getBest10SeriesByRating(){
        System.out.println("------------------------------------------------------------");
        System.out.println("These are the top 10 series:");
        //TODO print the name of the top 10 series - one by line
        this.series.stream().sorted(Comparator.comparing(Series::getRating).reversed()).limit(10).map(Series::getName).forEach(s->System.out.println(s));
        System.out.println("------------------------------------------------------------");
    }

    private void getAllGenres(){
        System.out.println("------------------------------------------------------------");
        System.out.println("These are all the genres found:");
        //TODO print all the genres of the series sorted alphabetically, they can not be repeated - one by line
        List<List<String>> genres=this.series.stream().map(Series::getGenres).collect(Collectors.toList());
        List<String> genres2=genres.stream().flatMap(List::stream).collect(Collectors.toList());
        genres2.stream().distinct().sorted().forEach(s->System.out.println(s));


        System.out.println("------------------------------------------------------------");
    }

    private void getSeriesByStudioShaft(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Series by Studio Shaft:");
        //TODO print the name of all Studio 'Shaft' series - one by line
        this.series.stream().filter(s->s.getStudios().contains("Shaft")).map(Series::getName).forEach(s->System.out.println(s));
        System.out.println("------------------------------------------------------------");
    }

    private void getMostEpisodesSeries(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Show with most episodes:");
        //TODO print the name and the episode count of the show with the most episodes
        this.series.stream().filter(s->s.getEpisodes()==this.series.stream().mapToInt(i->i.getEpisodes()).max().getAsInt()).forEach(s-> System.out.println(s.getName()+" "+ s.getEpisodes()));
        System.out.println("------------------------------------------------------------");
    }

    private void getBestStudio(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Best Studio:");
        //TODO print the name and the average rating of the best Studio
        this.series.stream().flatMap(x-> x.getStudios().stream().map(y ->new Pair<>(y,x.getRating()))).collect(Collectors.groupingBy(Pair::getKey,Collectors.averagingDouble(Pair::getValue)))
                .entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(1).forEach(System.out::println);

        System.out.println("------------------------------------------------------------");
    }

    private void getBestGenre(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Best genre:");
        //TODO print the name and the average rating of the best Genre
        this.series.stream().flatMap(x-> x.getGenres().stream().map(y ->new Pair<>(y,x.getRating()))).collect(Collectors.groupingBy(Pair::getKey,Collectors.averagingDouble(Pair::getValue)))
                .entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(1).forEach(System.out::println);
        System.out.println("------------------------------------------------------------");
    }

    private void getWorstGenre(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Worst genre:");
        //TODO print the name and the average rating of the worst Genre
        this.series.stream().flatMap(x-> x.getGenres().stream().map(y ->new Pair<>(y,x.getRating()))).collect(Collectors.groupingBy(Pair::getKey,Collectors.averagingDouble(Pair::getValue)))
                .entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue()).limit(1).forEach(i-> System.out.println(i.getKey() + i.getValue()));
        System.out.println("------------------------------------------------------------");
    }

    private void getTop5MostCommmonEpisodeCount(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Top 5 episode count:");
        //TODO print the count and value of the top 5 most common episode count  - example:  100 shows have 25 episodes
        this.series.stream().map(s-> s.getEpisodes()).map(x->new Pair<>(x,1)).collect(Collectors.groupingBy(Pair::getKey,Collectors.summingInt(Pair::getValue))).entrySet().stream()
        .sorted(Map.Entry.<Integer,Integer>comparingByValue().reversed()).limit(5).forEach(i-> System.out.println(i.getValue()+" shows have "+i.getKey()+" espisodes"));
        System.out.println("------------------------------------------------------------");
    }

    private void getAverageRatingOfCommedySeries(){
        System.out.println("------------------------------------------------------------");
        double average = 0;
        //TODO get the average rating of the 'Comedy' shows, put the result in average
        average=this.series.stream().filter(i->i.getGenres().contains("Comedy")).mapToDouble(i->i.getRating()).average().getAsDouble();
        System.out.println(String.format("Average rating of comedy series: %f",average));
        System.out.println("------------------------------------------------------------");
    }

    private void getMostCommonGenreWhereSugitaTomokazuActs(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Sugita Tomokazu most common genre:");
        //TODO print the most common genre where 'Sugita,Tomokazu' acts - the most common genre of the shows where 'Sugita,Tomokazu' is in mainCast
        this.series.stream().filter(s->s.getMainCast().contains("Sugita,Tomokazu")).flatMap(s-> s.getGenres().stream().map(x->new Pair<>(x,1))).collect(Collectors.groupingBy(Pair::getKey,Collectors.summingInt(Pair::getValue))).entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed()).limit(1).forEach(i-> System.out.println(i.getKey()));
        System.out.println("------------------------------------------------------------");
    }

    private void getBestActor(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Best Actor:");
        //TODO print the name and the average rating of the best Actor
        this.series.stream().flatMap(x-> x.getMainCast().stream().map(y ->new Pair<>(y,x.getRating()))).collect(Collectors.groupingBy(Pair::getKey,Collectors.averagingDouble(Pair::getValue)))
                .entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(1).forEach(i-> System.out.println(i.getKey() +" "+ i.getValue()));
        System.out.println("------------------------------------------------------------");

    }

    private void getBestShounenStudio(){
        System.out.println("------------------------------------------------------------");
        System.out.println("Best Shounen Studio:");
        //TODO print the name and the average rating (of the shounen series) of the best Shounen Studio - the studio with the best 'Shounen' (genre) series
        this.series.stream().filter(s->s.getGenres().contains("Shounen")).flatMap(x-> x.getStudios().stream().map(y ->new Pair<>(y,x.getRating()))).collect(Collectors.groupingBy(Pair::getKey,Collectors.averagingDouble(Pair::getValue)))
                .entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(1).forEach(System.out::println);
        System.out.println("------------------------------------------------------------");

    }




}
