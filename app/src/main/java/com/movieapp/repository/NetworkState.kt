package com.movieapp.repository


enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {

    companion object {
        val LOADED: NetworkState = NetworkState(
            Status.SUCCESS,
            "Success"
        )
        val LOADING: NetworkState = NetworkState(
            Status.RUNNING,
            "Running"
        )
        val ERROR: NetworkState = NetworkState(
            Status.FAILED,
            "Some thing is went wrong"
        )
        val END_OF_LIST: NetworkState = NetworkState(
            Status.FAILED, "you have reached th end"
        )
    }


}