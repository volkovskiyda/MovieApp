package com.gmail.volkovskiyda.movieapp

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

object DataSource {

    fun getMovieList(): Flow<List<Movie>> = Firebase.firestore.collection("movies").asFlow()
    /*
    fun getMovieList(): List<Movie> = listOf(
        Movie(
            title = "Dolittle",
            duration = "126 min",
            image = R.drawable.pic_movie_image_in_list_dolittle,
            imageBackground = R.drawable.pic_movie_image_in_details_dolittle,
            genre = "Adventure, Comedy, Family",
            rating = "PG",
            storyline = "A physician who can talk to animals embarks on an adventure to find a legendary island with a young apprentice and a crew of strange pets.",
            actors = listOf(
                Actor("Robert Downey Jr.", R.drawable.pic_actor_photo_robert_downey_jr),
                Actor("Antonio Banderas", R.drawable.pic_actor_photo_antonio_banderas),
                Actor("Michael Sheen", R.drawable.pic_actor_photo_michael_sheen),
                Actor("Jim Broadbent", R.drawable.pic_actor_photo_jim_broadbent),
                Actor("Jessie Buckley", R.drawable.pic_actor_photo_jessie_buckley),
                Actor("Harry Colett", R.drawable.pic_actor_photo_harry_colett),
            ),
        ),
        Movie(
            title = "Underwater",
            duration = "126 min",
            image = R.drawable.pic_movie_image_in_list_underwater,
            imageBackground = R.drawable.pic_movie_image_in_details_underwater,
            genre = "Action, Horror, Sci-Fi",
            rating = "18+",
            storyline = "A crew of oceanic researchers working for a deep sea drilling company try to get to safety after a mysterious earthquake devastates their deepwater research and drilling facility located at the bottom of the Mariana Trench.",
            actors = listOf(
                Actor("Kristen Stewart", R.drawable.pic_actor_photo_kristen_stewart),
                Actor("Vincent Cassel", R.drawable.pic_actor_photo_vincent_cassel),
                Actor("Mamoudou Athie", R.drawable.pic_actor_photo_mamoudou_athie),
                Actor("T. J. Miller", R.drawable.pic_actor_photo_tj_miller),
                Actor("John Gallagher Jr.", R.drawable.pic_actor_photo_john_gallagher_jr),
                Actor("Jessica Henwick", R.drawable.pic_actor_photo_jessica_henwick),
                Actor("Gunner Wright", R.drawable.pic_actor_photo_gunner_wright),
                Actor("Fiona Rene", R.drawable.pic_actor_photo_fiona_rene),
            ),
        ),
        Movie(
            title = "The Call Of The Wild",
            duration = "126 min",
            image = R.drawable.pic_movie_image_in_list_the_call_of_the_wild,
            imageBackground = R.drawable.pic_movie_image_in_details_the_call_of_the_wild,
            genre = "Adventure, Drama, Family",
            rating = "PG",
            storyline = "A sled dog struggles for survival in the wilds of the Yukon.",
            actors = listOf(
                Actor("Harrison Ford", R.drawable.pic_actor_photo_harrison_ford),
                Actor("Omar Sy", R.drawable.pic_actor_photo_omar_sy),
                Actor("Cara Gee", R.drawable.pic_actor_photo_cara_gee),
                Actor("Dan Stevens", R.drawable.pic_actor_photo_dan_stevens),
                Actor("Bradley Whitford", R.drawable.pic_actor_photo_bradley_whitford),
                Actor("Jean Louisa Kelly", R.drawable.pic_actor_photo_jean_louisa_kelly),
            ),
        ),
        Movie(
            title = "Last Christmas",
            duration = "126 min",
            image = R.drawable.pic_movie_image_in_list_last_christmas,
            imageBackground = R.drawable.pic_movie_image_in_details_last_christmas,
            genre = "Comedy, Drama, Romance",
            rating = "13+",
            storyline = "Kate is a young woman subscribed to bad decisions. Working as an elf in a year round Christmas store is not good for the wannabe singer. However, she meets Tom there. Her life takes a new turn. For Kate, it seems too good to be true.",
            actors = listOf(
                Actor("Emilia Clarke", R.drawable.pic_actor_photo_emilia_clarke),
                Actor("Madison Ingoldsby", R.drawable.pic_actor_photo_madison_ingoldsby),
                Actor("Emma Thompson", R.drawable.pic_actor_photo_emma_thompson),
                Actor("Boris Isakovic", R.drawable.pic_actor_photo_boris_isakovich),
                Actor("Maxim Baldry", R.drawable.pic_actor_photo_maxim_baldry),
            ),
        ),
        Movie(
            title = "The Invisible Guest",
            duration = "126 min",
            image = R.drawable.pic_movie_image_in_list_the_invisible_guest,
            imageBackground = R.drawable.pic_movie_image_in_details_the_invisible_guest,
            genre = "Crime, Drama, Mystery",
            rating = "16+",
            storyline = "A successful entrepreneur accused of murder and a witness preparation expert have less than three hours to come up with an impregnable defense.",
            actors = listOf(
                Actor("Mario Casas", R.drawable.pic_actor_photo_mario_casas),
                Actor("Ana Wagener", R.drawable.pic_actor_photo_ana_wagener),
                Actor("Barbara Lennie", R.drawable.pic_actor_photo_barbara_lennie),
                Actor("Francesc Orella", R.drawable.pic_actor_photo_francesc_orella),
                Actor("Paco Tous", R.drawable.pic_actor_photo_paco_tous),
            ),
        ),
        Movie(
            title = "Fantasy Island",
            duration = "126 min",
            image = R.drawable.pic_movie_image_in_list_fantasy_island,
            imageBackground = R.drawable.pic_movie_image_in_details_fantasy_island,
            genre = "Action, Adventure, Fantasy",
            rating = "13+",
            storyline = "When the owner and operator of a luxurious island invites a collection of guests to live out their most elaborate fantasies in relative seclusion, chaos quickly descends.",
            actors = listOf(
                Actor("Michael Pena", R.drawable.pic_actor_photo_michael_pena),
                Actor("Maggie Q", R.drawable.pic_actor_photo_maggie_q),
                Actor("Lucy Hale", R.drawable.pic_actor_photo_lucy_hale),
                Actor("Austin Stowell", R.drawable.pic_actor_photo_austin_stowell),
                Actor("Jimmy O. Yang", R.drawable.pic_actor_photo_jummy_o_yang),
                Actor("Portia Doubleday", R.drawable.pic_actor_photo_portia_doubleday),
            ),
        ),
    )
     */
}