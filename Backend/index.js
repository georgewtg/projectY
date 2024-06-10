require("dotenv").config();
const express = require('express');
const cors = require('cors');
const multer = require('multer');
const bodyParser = require('body-parser');
const accountRepo = require('./repositories/repository.account');
const postRepo = require('./repositories/repository.post');
const commentRepo = require('./repositories/repository.comment');
const likeRepo = require('./repositories/repository.like');
const followingRepo = require('./repositories/repository.following');
const googleRepo = require('./repositories/repository.google');

const port = process.env.port;
const app = express();
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, 'uploads/');
    },
    filename: function (req, file, cb) {
      cb(null, file.originalname);
    },
});
const upload = multer({ storage: storage }); // directory for temp files


// Middleware
app.use(bodyParser.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());


// Endpoint
app.get('/status', (req, res) => {
    res.status(200).json({ status: 'Server is running' });
});

// Account
app.post('/register', accountRepo.registerAccount);
app.post('/login', accountRepo.loginAccount);
app.put('/editAccount', accountRepo.editAccount);
app.get('/getAccounts', accountRepo.getAccounts);
app.post('/getAccountById', accountRepo.getAccountById);
app.delete('/deleteAccount', accountRepo.deleteAccount);

// Post
app.post('/addPost', postRepo.addPost);
app.get('/getAllPosts', postRepo.getAllPosts);
app.post('/getPostById', postRepo.getPostById);
app.post('/getPostByUser', postRepo.getPostByUser);
app.get('/getTopPosts', postRepo.getTopPosts);
app.get('/getRecentPosts', postRepo.getRecentPosts);
app.delete('/deletePost', postRepo.deletePost);

// Comment
app.post('/addComment', commentRepo.addComment);
app.put('/editComment', commentRepo.editComment);
app.get('/getAllComments', commentRepo.getAllComments);
app.post('/getCommentByPost', commentRepo.getCommentByPost);
app.post('/getCommentByUser', commentRepo.getCommentByUser);
app.get('/getCommentById', commentRepo.getCommentById);
app.delete('/deleteComment', commentRepo.deleteComment);

// Like
app.post('/addLike', likeRepo.addLike);
app.get('/getAllLikes', likeRepo.getAllLikes);
app.get('/getLikeByPost', likeRepo.getLikeByPost);
app.post('/getLikeByUser', likeRepo.getLikeByUser);
app.post('/getLikeById', likeRepo.getLikeById);
app.delete('/deleteLike', likeRepo.deleteLike);

// Following
app.post('/addFollowing', followingRepo.addFollowing);
app.get('/getAllFollowings', followingRepo.getAllFollowings);
app.post('/getFollowers', followingRepo.getFollowers);
app.post('/getFolloweds', followingRepo.getFolloweds);
app.delete('/deleteFollowing', followingRepo.deleteFollowing);

// Google
app.post('/uploadProfile', upload.single('file'), googleRepo.uploadProfile);
app.post('/uploadPost', upload.single('file'), googleRepo.uploadPost);
app.delete('/deleteImage', googleRepo.deleteFile);


app.listen(port, () => {
    console.log("🚀 Server is running and listening on port", port);
});