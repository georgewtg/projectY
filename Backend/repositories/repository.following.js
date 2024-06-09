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
    console.log("💡 Following Connected to PostgreSQL database");
});

async function addFollowing(req, res) {
    const {follower_id, followed_id} = req.body

    try {
        const result = await pool.query(
            `INSERT INTO followings (follower_id, followed_id)
            VALUES ($1, $2) RETURNING *`,
            [follower_id, followed_id]
        );
        const newFollowing = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Successfully Followed the user",
            payload: newFollowing,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getAllFollowings(req, res) {
    try {
        const result = await pool.query(
            `SELECT * FROM followings`
        );
        const followings = result.rows;
        res.status(201).json({
            success: true,
            message: "Followings found",
            payload: followings,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getFollowers(req, res) {
    const {user_id} = req.body

    try {
        const result = await pool.query(
            `SELECT follower_id FROM followings
            WHERE followed_id = $1`,
            [user_id]
        );
        const followers = result.rows;
        res.status(201).json({
            success: true,
            message: "User's followers found",
            payload: followers,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getFolloweds(req, res) {
    const {user_id} = req.body

    try {
        const result = await pool.query(
            `SELECT followed_id FROM followings
            WHERE follower_id = $1`,
            [user_id]
        );
        const followeds = result.rows;
        res.status(201).json({
            success: true,
            message: "User's followeds found",
            payload: followeds,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function deleteFollowing(req, res) {
    const {follower_id, followed_id} = req.body

    try {
        const result = await pool.query(
            `DELETE FROM likes
            WHERE follower_id = $1 AND followed_id = $2`,
            [follower_id, followed_id]
        );
        res.status(201).json({
            success: true,
            message: "Following deleted",
            payload: null,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

module.exports = {
    addFollowing,
    getAllFollowings,
    getFollowers,
    getFolloweds,
    deleteFollowing,
};