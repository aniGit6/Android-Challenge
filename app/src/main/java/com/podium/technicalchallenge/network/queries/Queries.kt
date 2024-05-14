package com.podium.technicalchallenge.network.queries

object Queries {

    enum class MOVIE_SORT_DIRECTION {
        ASC,
        DESC
    }
    fun getMoviesPagingQuery(limit: Int, offset: Int, order: String, sort: MOVIE_SORT_DIRECTION) =
    """
        query GetMoviesPagingQuery {
            movies(limit: $limit, offset: $offset, orderBy: "$order", sort: ${sort.name}) {
                id
                title
                overview
            }
        }
    """

    fun getGenres() =
        """
        query GetGenres {
            genres
        }
    """

    fun getMoviesByGenre(limit: Int, offset: Int = 0, genre: String) =
        """
            query GetMoviesByGenre {
            movies(limit: $limit ,offset: $offset, genre: "$genre") {
                id
                title
                overview
             }
            }
        """

    fun getMovieDetails(id: Int) =
        """
        query GetMovieDetails {
            movie(id: $id) {
                title
                overview
                posterPath
                popularity
                genres
                cast {
                    profilePath
                    name
                    character
                    order
                }
                director {
                    id
                    name
                }
            }
        }
    """
}
