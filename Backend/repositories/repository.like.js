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
    console.log("💡 Like Connected to PostgreSQL database");
});

async function addLike(req, res) {
    const {post_id, user_id} = req.body

    try {
        const result = await pool.query(
            `INSERT INTO likes (post_id, user_id)
            VALUES ($1, $2) RETURNING *`,
            [post_id, user_id]
        );
        const newLike = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Successfully Liked the post",
            payload: newLike,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getAllLikes(req, res) {
    try {
        const result = await pool.query(
            `SELECT * FROM likes`
        );
        const likes = result.rows;
        res.status(201).json({
            success: true,
            message: "Likes found",
            payload: likes,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getLikeByPost(req, res) {
    const {post_id} = req.body

    try {
        const result = await pool.query(
            `SELECT * FROM likes
            WHERE post_id = $1`,
            [post_id]
        );
        const likes = result.rows;
        res.status(201).json({
            success: true,
            message: "Post's likes found",
            payload: likes,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getLikeByUser(req, res) {
    const {user_id} = req.body

    try {
        const result = await pool.query(
            `SELECT * FROM likes
            WHERE user_id = $1`,
            [user_id]
        );
        const likes = result.rows;
        res.status(201).json({
            success: true,
            message: "User's likes found",
            payload: likes,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getLikeById(req, res) {
    const {like_id} = req.body

    try {
        const result = await pool.query(
            `SELECT * FROM likes
            WHERE like_id = $1`,
            [like_id]
        );
        const like = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Like found",
            payload: like,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function deleteLike(req, res) {
    const {like_id} = req.body

    try {
        const result = await pool.query(
            `DELETE FROM likes
            WHERE like_id = $1`,
            [like_id]
        );
        res.status(201).json({
            success: true,
            message: "Like deleted",
            payload: null,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

module.exports = {
    addLike,
    getAllLikes,
    getLikeByPost,
    getLikeByUser,
    getLikeById,
    deleteLike,
};