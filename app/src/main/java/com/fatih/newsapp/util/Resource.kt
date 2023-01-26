package com.fatih.newsapp.util

class Resource <T>(val data:T?,val message:String?,val status:Status) {

    companion object{

        fun <T> success(data:T):Resource<T>{
            return Resource(data,null,Status.SUCCESS)
        }
        fun <T> loading(data:T?,message: String?):Resource<T>{
            return Resource(data,message,Status.LOADING)
        }
        fun <T> error(message: String):Resource<T>{
            return Resource(null,message,Status.ERROR)
        }
    }

}

enum class Status{
    LOADING,SUCCESS,ERROR
}