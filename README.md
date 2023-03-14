1. Get Comment
This api gives the comments of a particular post.


curl --location 'http://localhost:8080/rest/v1/user/2/post/1/comment' \
--header 'auth-token: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsInN1YiI6InVzZXItbWFuYWdlbWVudC1zZWN1cml0eSIsImp0aSI6Ijc2MmI0MjBjLTVkMzAtNDUyMC04Y2FhLTdkOWM4ODUxOWVkNSIsImlhdCI6MTY3NTAyMTExMiwiZXhwIjoxNjc1MDI0NzEyfQ.nDe2qsaRFJNUS4efhfTAZk6aop2XL2CK5qDQDtDLcGs'


2. Create User
This api is used to create new user.


curl --location 'http://localhost:8080/rest/v1/user' \
--header 'Content-Type: application/json' \
--data-raw '{
 "userName":"",
 "password":"sanjana@123#",
 "firstName":"sanjana",
 "lastName":"shekhawat",
 "dob":"02/07/1998",
 "email":"sanjana134@gmain.com",
 "phoneNo":"9868948459"
}'


3.Update User
This api is used to update details of user.


curl --location --request PUT 'http://localhost:8080/rest/v1/user/1' \
--header 'Content-Type: application/json' \
--data '{
"lastName":"chaudhary",
 "dob":"15/10/1997"
}'


4.Login User
This api is used to login.


curl --location 'http://localhost:8080/rest/v1/user/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userName":"poonam123",
    "password":"poonam@123#"
}'


5.Delete User
This api is used to delete a particular user.


curl --location --request DELETE 'http://localhost:8080/rest/v1/user/3'


6.Logout User
This api is used to logout a user.


curl --location 'http://localhost:8080/rest/v1/user/2/logout' \
--header 'auth-token: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsInN1YiI6InVzZXItbWFuYWdlbWVudC1zZWN1cml0eSIsImp0aSI6IjRiNDFhYWZiLTE3MjQtNDk5ZC1hOTI5LWI4OTczNTU4MzQwZiIsImlhdCI6MTY3NTAyMDM0NCwiZXhwIjoxNjc1MDIzOTQ0fQ.V5gpnUZ9V9vS-4nLZ7YRUpbK4q1XoDc8nswJ7FuunRM'


7.Create Post
This api is used to create post of an user.


curl --location 'http://localhost:8080/rest/v1/user/4/post' \
--header 'Content-Type: text/plain' \
--data '{
    "message":"Changed a lot..!!"
}'


8.Update Post
This api is used to update details of a particular post of an user.


curl --location --request PUT 'http://localhost:8080/rest/v1/user/1/post/2' \
--header 'Content-Type: application/json' \
--data '{
    "message":"Loves travelling..!!"
}'


9.Get Post
This api is used to get particular post of an user.


curl --location 'http://localhost:8080/rest/v1/user/4/post/1'


10.Delete Post
This api is used to delete a particular post of an user.


curl --location --request DELETE 'http://localhost:8080/rest/v1/user/2/post/3'



11.Get All Post
This api is used to get all post of an user.


curl --location 'http://localhost:8080/rest/v1/user/4/post' \
--data ''



12.Create Comment
This api is used to create comment on a post of an User.


curl --location 'http://localhost:8080/rest/v1/user/2/post/1/comment' \
--header 'Content-Type: application/json' \
--data '{
    "message":"you look good..!",
    "commentorUserId":4
}'


13.Get Comment
This api is used to get all the comments on a particular post of an user.


curl --location 'http://localhost:8080/rest/v1/user/2/post/1/comment' \
--header 'auth-token: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsInN1YiI6InVzZXItbWFuYWdlbWVudC1zZWN1cml0eSIsImp0aSI6Ijc2MmI0MjBjLTVkMzAtNDUyMC04Y2FhLTdkOWM4ODUxOWVkNSIsImlhdCI6MTY3NTAyMTExMiwiZXhwIjoxNjc1MDI0NzEyfQ.nDe2qsaRFJNUS4efhfTAZk6aop2XL2CK5qDQDtDLcGs'


14.Delete Comment
This api is used to delete a particular comment from a post of an user.


curl --location --request DELETE 'http://localhost:8080/rest/v1/user/2/post/1/comment/12'



15.Update Comment
This api is used to update a particular comment on a post of an user.


curl --location --request PUT 'http://localhost:8080/rest/v1/user/2/post/1/comment/2' \
--header 'Content-Type: application/json' \
--data '{
  "message":"Hey gorgeous,how do you do..!"  
}'


16.Create Like
This api is used to create like on a post of an user.


curl --location 'http://localhost:8080/rest/v1/user/2/post/1/like' \
--header 'Content-Type: application/json' \
--data '{
    "user":{
        "userId":4
    }
}'


17.Delete Like
This api is used to delete a particular like on a post of an user.


curl --location --request DELETE 'http://localhost:8080/rest/v1/user/2/post/1/like/1'


18.Get Likes
This api is used to get all the likes on a post of an user.


curl --location 'http://localhost:8080/rest/v1/user/2/post/1/like'


19.Add Friend
This api is used to add a friend.


curl --location 'http://localhost:8080/rest/v1/user/5/friend' \
--header 'Content-Type: application/json' \
--data '{
    "user2Id":6
}'


20.Get Friend
This api is used to get all the friends of an user.


curl --location 'http://localhost:8080/rest/v1/user/2/friend'


21.Change Password
This api is used to change password of an user.


curl --location 'http://localhost:8080/rest/v1/user/change_password' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userName":"poonam@123",
    "oldPassword":"Bharat@01",
    "newPassword":"Bharat@567",
    "confirmPassword":"Bharat@567"
}'
