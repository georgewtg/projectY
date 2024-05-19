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
    console.log("💡 Account Connected to PostgreSQL database");
});

async function registerAccount(req, res) {
    const {username, email, password} = req.body

    try {
        const result = await pool.query(
            `INSERT INTO accounts (username, email, password)
             VALUES ($1, $2, $3) RETURNING *`,
            [username, email, password]
        );
        const newAccount = result.rows[0];
        res.status(201).json(newAccount);
    } catch (error) {
        res.status(500).json({error: "Internal Server Error"});
    }
}

async function loginAccount(req, res) {}

module.exports = {
    registerAccount,
    loginAccount,
};