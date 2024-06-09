const { Pool } = require("pg");

const pool = new Pool({
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    host: process.env.DB_HOST,
    database: process.env.DB_NAME,
    
    ssl: {
        require: true,
    }
});

pool.connect().then(() => {
    console.log("💡 Post Connected to PostgreSQL database");
});

async function addPost(req, res) {
    const {user_id, post_img_id} = req.body

    try {
        const result = await pool.query(
            `INSERT INTO posts (user_id, post_img_id)
            VALUES ($1, $2) RETURNING *`,
            [user_id, post_img_id]
        );
        const newPost = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Successfully posted image",
            payload: newPost,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getAllPosts(req, res) {
    try {
        const result = await pool.query(
            `SELECT * FROM posts`
        );
        const posts = result.rows;
        res.status(201).json({
            success: true,
            message: "Posts found",
            payload: posts,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getPostByUser(req, res) {
    const {user_id} = req.body

    try {
        const result = await pool.query(
            `SELECT * FROM posts
            WHERE user_id = $1`,
            [user_id]
        );
        const posts = result.rows;
        res.status(201).json({
            success: true,
            message: "User's posts found",
            payload: posts,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getPostById(req, res) {
    const {post_id} = req.body

    try {
        const result = await pool.query(
            `SELECT * FROM posts
            WHERE post_id = $1`,
            [post_id]
        );
        const post = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Post found",
            payload: post,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getTopPosts(req, res) {
    try {
        const result = await pool.query(
            `SELECT posts.post_id, posts.user_id, post_img_id, post_time, COUNT(like_id) AS likes
            FROM posts
            INNER JOIN likes ON posts.post_id = likes.post_id
            GROUP BY posts.post_id, posts.user_id, post_img_id, post_time
            ORDER BY likes DESC`
        );
        const topPosts = result.rows;
        res.status(201).json({
            success: true,
            message: "Top Posts found",
            payload: topPosts,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getRecentPosts(req, res) {
    try {
        const result = await pool.query(
            `SELECT * FROM posts
            ORDER BY post_time DESC`
        );
        const recentPosts = result.rows;
        res.status(201).json({
            success: true,
            message: "Recent Posts found",
            payload: recentPosts,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function deletePost(req, res) {
    const {post_id} = req.body

    try {
        const result = await pool.query(
            `DELETE FROM posts
            WHERE post_id = $1`,
            [post_id]
        );
        res.status(201).json({
            success: true,
            message: "Post deleted",
            payload: null,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

module.exports = {
    addPost,
    getAllPosts,
    getPostByUser,
    getPostById,
    getTopPosts,
    getRecentPosts,
    deletePost,
};