               package com.itheima.javarbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

               @Data
               @NoArgsConstructor
               @AllArgsConstructor
               public class Result {
                   private long status;
                   private String message;
                   private Object data;

                   public static Result success(long status,String message,Object data){
                       return new Result(status,message,data);
                   }

                   public static Result success(long status,String message){
                       return new Result(status,message,null);
                   }

                   public static Result error(String message,Object data){
                       return new Result(500,message,data);
                   }

                   public static Result error(String message){
                       return new Result(500,message,null);
                   }

               }
