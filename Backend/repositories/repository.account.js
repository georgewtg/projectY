const { Pool } = require("pg");
const crypto = require("crypto");

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
    console.log("💡 Account Connected to PostgreSQL database");
});

async function registerAccount(req, res) {
    const {username, email, password, profile_img_id} = req.body


    // hashing
    generatedPassword = null;
    try {
        const hash = crypto.createHash('md5');
        hash.update(password);
        generatedPassword = hash.digest('hex');
    } catch (error) {
        console.log("failed to hash password");
    }


    // Post Account to Database
    try {
        const result = await pool.query(
            `INSERT INTO accounts (username, email, password, profile_img_id)
            VALUES ($1, $2, $3, $4) RETURNING *`,
            [username, email, generatedPassword, profile_img_id]
        );
        const newAccount = result.rows[0];
        res.status(201).json({
            success: true,
            message: "successfully registered",
            payload: newAccount,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function loginAccount(req, res) {
    const {email, password} = req.body


    // hashing
    generatedPassword = null;
    try {
        const hash = crypto.createHash('md5');
        hash.update(password);
        generatedPassword = hash.digest('hex');
    } catch (error) {
        console.log("failed to hash password");
    }


    // Search Account in Database
    try {
        const result = await pool.query(
            `SELECT * FROM accounts
            WHERE email = $1
            AND password = $2`,
            [email, generatedPassword]
        );
        const loggedAccount = result.rows[0];
        res.status(201).json({
            success: true,
            message: "successfully logged in",
            payload: loggedAccount,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getAccounts(req, res) {
    try {
        const result = await pool.query(
            `SELECT * FROM accounts`
        );
        const accounts = result.rows;
        res.status(201).json({
            success: true,
            message: "Accounts found",
            payload: accounts,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function getAccountById(req, res) {
    const {user_id} = req.body;

    try {
        const result = await pool.query(
            `SELECT * FROM accounts
            WHERE user_id = $1`,
            [user_id]
        );
        const account = result.rows[0];
        res.status(201).json({
            success: true,
            message: "Account found",
            payload: account,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function editAccount(req, res) {
    const {user_id, username, email, password, profile_img_id} = req.body


    // hashing
    generatedPassword = null;
    try {
        const hash = crypto.createHash('md5');
        hash.update(password);
        generatedPassword = hash.digest('hex');
    } catch (error) {
        console.log("failed to hash password");
    }


    // Post Account to Database
    try {
        const result = await pool.query(
            `UPDATE accounts SET
            username = $1,
            password = $2,
            profile_img_id = $3,
            WHERE user_id = $4 AND email = $5 RETURNING *`,
            [username, generatedPassword, profile_img_id, user_id, email]
        );
        const account = result.rows[0];
        res.status(201).json({
            success: true,
            message: "successfully updated",
            payload: account,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function deleteAccount(req, res) {
    const {user_id} = req.body

    try {
        const result = await pool.query(
            `DELETE FROM accounts
            WHERE user_id = $1`,
            [user_id]
        );
        res.status(201).json({
            success: true,
            message: "Account deleted",
            payload: null,
        });
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

module.exports = {
    registerAccount,
    loginAccount,
    getAccounts,
    getAccountById,
    editAccount,
    deleteAccount,
};