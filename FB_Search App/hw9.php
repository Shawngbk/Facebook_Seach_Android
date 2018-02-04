<?php
    header("Access-Control-Allow-Origin:* ");
    header("Content-Type:application/json");

    ini_set('session.cache_limiter','public');
    session_cache_limiter(false);
    session_start();
                        //$_session['keyword']=$_session['select_type']=$_session['locations']=$_session['distance'] = "";


//$kw_data = json_decode($json, true);
//var_dump($kw_data);
$user_Json = $page_Json = $event_Json = $place_Json = $group_Json = "";
//--------------------------------------user---------------------------------------
if(isset($_GET['keywords'])){
        //$url_page = 'https://graph.facebook.com/v2.8/search?';
        //$url_q = "q=".urlencode("spacex")."&";
        //$url_type = "type=".urlencode("user")."&";
        //$url_fields = "fields=".urlencode("id,name,picture.width(700).height(700)");
        //$url_accToken = "&access_token=".urlencode("EAAFydU6luHcBAJBdDKM0IXusysZA8ZAeaNjMYaGMCZBb9p3LoDTihW3H2pSzACGdYjiQDNXF9Cv3PKvU5ZCkVKoSJqKykgofoZBRCLIvzvh7IVopGqv7meRt1t6VvnQEUPRrCF63hpuPk2jMMJPZAE8dwrwWiL5FYZD");

        if(isset($_GET['page_url']) == false){

            $url_page = 'https://graph.facebook.com/v2.8/search?';
                    $url_q = "q=".urlencode($_GET['keywords'])."&";
                    //$url_q = "q="."spacex"."&";
                    $user_url_type = "type="."user"."&";
                    $page_url_type = "type="."page"."&";
                    $event_url_type = "type="."event"."&";
                    $place_url_type = "type="."place"."&";
                    $group_url_type = "type="."group"."&";
                    $url_fields = "fields="."id,name,picture.width(700).height(700)&limit=10";
                    $url_accToken = "&access_token="."EAARKJzroutgBAM3uNwiXXQZAN2pSbSrunOnZCuhun9nlkk7ZBG2Sc4x1k66DDrpZCFRDMXkBCgEHTEiZAuZATrKe7b0MWNnVnMOJ3NSDqVhsO3vLkSIsfVScpkzKES1RwxAlmwhxX4tm8ZBttJd21DZBg4oC11TnZALoZD";


            $user_url = $url_page.$url_q.$user_url_type.$url_fields.$url_accToken;
            $page_url = $url_page.$url_q.$page_url_type.$url_fields.$url_accToken;
            $event_url = $url_page.$url_q.$event_url_type.$url_fields.$url_accToken;
            $place_url = $url_page.$url_q.$place_url_type.$url_fields.$url_accToken;
            $group_url = $url_page.$url_q.$group_url_type.$url_fields.$url_accToken;
          //  var_dump($user_url);

            $user_Json = json_decode(file_get_contents($user_url));
            $page_Json = json_decode(file_get_contents($page_url));
            $event_Json = json_decode(file_get_contents($event_url));
            $place_Json = json_decode(file_get_contents($place_url));
            $group_Json = json_decode(file_get_contents($group_url));
                //var_dump( $user_Json);


           echo json_encode(array("user_obj"=>$user_Json, "page_obj"=>$page_Json, "event_obj"=>$event_Json, "place_obj"=>$place_Json, "group_obj"=>$group_Json));
                             // var_dump($place_url);

                             //var_dump(json_decode(json_encode(array("user_obj"=>$user_Json, "page_obj"=>$page_Json, "event_obj"=>$event_Json, "place_obj"=>$place_Json, "group_obj"=>$group_Json))));
        } else if(isset($_GET['page_url']) == true) {
           echo file_get_contents($_GET['page_url']);
        }
}
if(isset($_GET['id'])){
                    $album_post_page = 'https://graph.facebook.com/v2.8/';
                    $album_post_id = $_GET["id"] . "?";
                    //$album_post_id = "134972803193847" . "?";

                    $album_post_fields = "fields=" . urlencode("id,name,picture.width(700).height(700),albums.limit(5){name,photos.limit(2) {name, picture}},posts.limit(5)");
                    $album_post_token = "&access_token=" . urlencode("EAARKJzroutgBAM3uNwiXXQZAN2pSbSrunOnZCuhun9nlkk7ZBG2Sc4x1k66DDrpZCFRDMXkBCgEHTEiZAuZATrKe7b0MWNnVnMOJ3NSDqVhsO3vLkSIsfVScpkzKES1RwxAlmwhxX4tm8ZBttJd21DZBg4oC11TnZALoZD");
                    $album_post_url = $album_post_page . $album_post_id . $album_post_fields . $album_post_token;
                            //var_dump($album_post_url);
                    echo file_get_contents($album_post_url);
                    /*
                    $access_token = urlencode("EAARKJzroutgBAM3uNwiXXQZAN2pSbSrunOnZCuhun9nlkk7ZBG2Sc4x1k66DDrpZCFRDMXkBCgEHTEiZAuZATrKe7b0MWNnVnMOJ3NSDqVhsO3vLkSIsfVScpkzKES1RwxAlmwhxX4tm8ZBttJd21DZBg4oC11TnZALoZD");

                            $pic_url_search = $album_post_id . $album_post_fields;

                    try {
                        $response = $fb->get($pic_url_search, $access_token);

                                                                                              //print_r($fb_json);
                    } catch(Facebook\Exceptions\FacebookResponseException $e) {
                                                                                              // When Graph returns an error
                                    //echo 'Graph returned an error: ' . $e->getMessage();
                        exit;
                    } catch(Facebook\Exceptions\FacebookSDKException $e) {
                                                                                              // When validation fails or other local issues
                        echo 'Facebook SDK returned an error: ' . $e->getMessage();
                        exit;
                    }

                    $detailEdge = $response->getBody();
                                                                                               //var_dump($userEdge);
                    //$album_post_json = json_decode($userEdge);
                    echo $detailEdge;
                    //echo $album_post_url;
                    */
}
/*
else if(isset($_GET['page_url'])) {
    echo file_get_contents($_GET['page_url']));
}
*/

/*
        $user_url_search = 'search?'.$url_q.$user_url_type.$url_fields;
        $page_url_search = 'search?'.$url_q.$page_url_type.$url_fields;
        $event_url_search = 'search?'.$url_q.$event_url_type.$url_fields;
        $place_url_search = 'search?'.$url_q.$place_url_type.$url_fields;
        $group_url_search = 'search?'.$url_q.$group_url_type.$url_fields;

        try {
            $user_response = $fb->get($user_url_search);
            $page_response = $fb->get($page_url_search);
            $event_response = $fb->get($event_url_search);
            $place_response = $fb->get($place_url_search);
            $group_response = $fb->get($group_url_search);
            //print_r($fb_json);
        } catch(Facebook\Exceptions\FacebookResponseException $e) {
                                                                  // When Graph returns an error
            echo 'Graph returned an error: ' . $e->getMessage();
            exit;
        } catch(Facebook\Exceptions\FacebookSDKException $e) {
                                                                  // When validation fails or other local issues
                                    //echo 'Facebook SDK returned an error: ' . $e->getMessage();
            exit;
        }
        $userEdge = $user_response->getBody();
        $pageEdge = $page_response->getBody();
        $eventEdge = $event_response->getBody();
        $placeEdge = $place_response->getBody();
        $groupEdge = $group_response->getBody();

       //echo json_encode(array("user_obj"=>$userEdge, "page_obj"=>$pageEdge, "event_obj"=>$eventEdge, "place_obj"=>$placeEdge, "group_obj"=>$groupEdge));
                                                                                        // var_dump($userEdge);


        //$userJson = file_get_contents($url);
        //$userJson = json_decode($userJson);

        if(isset($_GET['user_next_page'])) {
            //$userJson = json_decode($userEdge);
            //$nextJson = $userJson->paging->next;
            //echo file_get_contents($_GET['user_next_page']);
        } else if(isset($_GET['user_pre_page'])) {
            //$userJson = json_decode($userEdge);
            //$preJson = $userJson->paging->previous;
            //echo file_get_contents($_GET['user_pre_page']);
        } else {
            //echo $userEdge;
        }

       // echo $userJson;
        //echo "<p>" . $userJson . "</p>";

*/




?>