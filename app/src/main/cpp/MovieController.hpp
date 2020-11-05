//
//  MovieController.hpp
//  Highrise
//
//  Created by Jimmy Xu on 12/19/18.
//  Copyright © 2019 Highrise. All rights reserved.
//

#ifndef MovieController_hpp
#define MovieController_hpp

#include <string>
#include <vector>
#include <map>

namespace movies {

    class Actor {
    public:
        std::string name;
        int age;
        //optional challenge 1: Load image from URL
        std::string imageUrl;
    };

    class Movie {
    public:
        int id;
        std::string name;
        int lastUpdated;
        float score;
        std::vector<Actor> actors;
        std::string description;
        std::string posterUrl;
    };

    class MovieController {
    private:
        std::vector<Movie *> _movies;
    public:
        MovieController() {
            //populate data
            for (int i = 0; i < 10; i++) {

                auto movie = new Movie();
                movie->id = i;
                movie->name = "Top Gun " + std::to_string(i);
                movie->lastUpdated = i * 10000;
                movie->score = rand() % 10;

                if (i % 3 == 0) {
                    movie->posterUrl = "https://www.wftv.com/resizer/5Y5lRAi-YHD_U09t7BW_G18JrK4=/1200x675/cloudfront-us-east-1.images.arcpublishing.com/cmg/KRZEOBULLVEKXPMUDRELO5XA7Q.jpg";
                } else if (i % 3 == 1) {
                    movie->posterUrl = "https://discoverbeaumont.com/wp-content/uploads/2019/06/TopGun_wenthumb.jpg";
                } else {
                    movie->posterUrl = "https://sothebys-md.brightspotcdn.com/b8/1c/41e05570498e97a6b26c472f5cfe/168l20895-bjb6b.jpg";
                }
                movie->description = "As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.";

                auto tomCruise = Actor();
                tomCruise.name = "Tom Cruise";
                tomCruise.age = 50;
                tomCruise.imageUrl = "https://pyxis.nymag.com/v1/imgs/4e5/1f7/a917c50e70a4c16bc35b9f0d8ce0352635-14-tom-cruise.rsquare.w700.jpg";
                movie->actors.push_back(tomCruise);

                auto valKilmer = Actor();
                valKilmer.name = "Val Kilmer";
                valKilmer.age = 46;
                valKilmer.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg";
                movie->actors.push_back(valKilmer);

                auto timRobbins = Actor();
                timRobbins.name = "Tim Robbins";
                timRobbins.age = 55;
                timRobbins.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg";
                movie->actors.push_back(timRobbins);

                auto jenniferConnelly = Actor();
                jenniferConnelly.name = "Jennifer Connelly";
                jenniferConnelly.age = 39;
                jenniferConnelly.imageUrl = "https://m.media-amazon.com/images/M/MV5BOTczNTgzODYyMF5BMl5BanBnXkFtZTcwNjk4ODk4Mw@@._V1_UY317_CR12,0,214,317_AL_.jpg";
                movie->actors.push_back(jenniferConnelly);

                _movies.push_back(movie);
            }
        }

        //Returns list of movies
        std::vector<Movie *> getMovies() {
            return _movies;
        }
    };
}

#endif /* MovieController_hpp */