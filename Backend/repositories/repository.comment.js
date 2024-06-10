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
    console.log("💡 Comment Connected to PostgreSQL database");
});

async function addComment(req, res) {
    const {post_id, user_id, comment} = req.body

    try {
        const result = await pool.query(
            `INSERT INTO comments (post_id, user_id, comment)
            VALUES ($1, $2, $3) RETURNING *`,
            [post_id, user_id, comment]
        );
        const newComment = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Successfully commented on post",
            payload: newComment,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function editComment(req, res) {
    const {post_id, user_id, comment} = req.body

    try {
        const result = await pool.query(
            `UPDATE comments SET
            comment = $3,
            is_edited = TRUE
            WHERE post_id = $1 AND user_id = $2
            RETURNING *`,
            [post_id, user_id, comment]
        );
        const comment = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Comment edited",
            payload: comment,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getAllComments(req, res) {
    try {
        const result = await pool.query(
            `SELECT * FROM comments`
        );
        const comments = result.rows;
        res.status(201).json({
            success: true,
            message: "Comments found",
            payload: comments,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getCommentByPost(req, res) {
    const {post_id} = req.body

    try {
        const result = await pool.query(
            `SELECT comment_id, comments.user_id, comment, is_edited, username, email, profile_img_id
            FROM comments INNER JOIN accounts
            ON comments.user_id = accounts.user_id
            WHERE post_id = $1`,
            [post_id]
        );
        const comments = result.rows;
        res.status(201).json({
            success: true,
            message: "Post's comments found",
            payload: comments,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getCommentByUser(req, res) {
    const {user_id} = req.body

    try {
        const result = await pool.query(
            `SELECT * FROM comments
            WHERE user_id = $1`,
            [user_id]
        );
        const comments = result.rows;
        res.status(201).json({
            success: true,
            message: "User's comments found",
            payload: comments,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getCommentById(req, res) {
    const {comment_id} = req.body

    try {
        const result = await pool.query(
            `SELECT * FROM comments
            WHERE comment_id = $1`,
            [comment_id]
        );
        const comment = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Comment found",
            payload: comment,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function deleteComment(req, res) {
    const {comment_id} = req.body

    try {
        const result = await pool.query(
            `DELETE FROM comments
            WHERE comment_id = $1`,
            [comment_id]
        );
        res.status(201).json({
            success: true,
            message: "Comment deleted",
            payload: null,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

module.exports = {
    addComment,
    editComment,
    getAllComments,
    getCommentByPost,
    getCommentByUser,
    getCommentById,
    deleteComment,
};