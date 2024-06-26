package ru.itmo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic checkOwnerExistsRequest() {
        return new NewTopic("check_owner_exists_request", 1, (short) 1);
    }

    @Bean
    public NewTopic checkOwnerExistsResponse() {
        return new NewTopic("check_owner_exists_response", 1, (short) 1);
    }

    @Bean
    public NewTopic getAllCatsRequest() {
        return new NewTopic("get_all_cats_request", 1, (short) 1);
    }

    @Bean
    public NewTopic getAllCatsResponse() {
        return new NewTopic("get_all_cats_response", 1, (short) 1);
    }

    @Bean
    public NewTopic getCatByIdRequest() {
        return new NewTopic("get_cat_by_id_request", 1, (short) 1);
    }

    @Bean
    public NewTopic getCatByIdResponse() {
        return new NewTopic("get_cat_by_id_response", 1, (short) 1);
    }

    @Bean
    public NewTopic createCatRequest() {
        return new NewTopic("create_cat_request", 1, (short) 1);
    }

    @Bean
    public NewTopic createCatResponse() {
        return new NewTopic("create_cat_response", 1, (short) 1);
    }

    @Bean
    public NewTopic deleteCatByIdRequest() {
        return new NewTopic("delete_cat_by_id_request", 1, (short) 1);
    }

    @Bean
    public NewTopic deleteCatByIdResponse() {
        return new NewTopic("delete_cat_by_id_response", 1, (short) 1);
    }

    @Bean
    public NewTopic getCatByColorRequest() {
        return new NewTopic("get_cat_by_color_request", 1, (short) 1);
    }

    @Bean
    public NewTopic getCatByColorResponse() {
        return new NewTopic("get_cat_by_color_response", 1, (short) 1);
    }

    @Bean
    public NewTopic makeFriendsRequest() {
        return new NewTopic("make_friends_request", 1, (short) 1);
    }

    @Bean
    public NewTopic makeFriendsResponse() {
        return new NewTopic("make_friends_response", 1, (short) 1);
    }

    @Bean
    public NewTopic createOwnerRequest() {
        return new NewTopic("create_owner_request", 1, (short) 1);
    }

    @Bean
    public NewTopic createOwnerResponse() {
        return new NewTopic("create_owner_response", 1, (short) 1);
    }

    @Bean
    public NewTopic getAllOwnersRequest() {
        return new NewTopic("get_all_owners_request", 1, (short) 1);
    }

    @Bean
    public NewTopic getAllOwnersResponse() {
        return new NewTopic("get_all_owners_response", 1, (short) 1);
    }

    @Bean
    public NewTopic getOwnerByIdRequest() {
        return new NewTopic("get_owner_by_id_request", 1, (short) 1);
    }

    @Bean
    public NewTopic getOwnerByIdResponse() {
        return new NewTopic("get_owner_by_id_response", 1, (short) 1);
    }

    @Bean
    public NewTopic getCatListRequest() {
        return new NewTopic("get_cat_list_request", 1, (short) 1);
    }

    @Bean
    public NewTopic getCatListResponse() {
        return new NewTopic("get_cat_list_response", 1, (short) 1);
    }

    @Bean
    public NewTopic deleteOwnerByIdRequest() {
        return new NewTopic("delete_owner_by_id_request", 1, (short) 1);
    }

    @Bean
    public NewTopic deleteOwnerByIdResponse() {
        return new NewTopic("delete_owner_by_id_response", 1, (short) 1);
    }
}
