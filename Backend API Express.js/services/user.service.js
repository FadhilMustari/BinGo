const dbPool = require('../config/database');

const register = async (body) => {
  const query = `INSERT INTO users (username, email, password, point) VALUES ('${body.username}', '${body.email}', '${body.password}', '${body.point}')`;
  return dbPool.execute(query);
};

const login = async (body) => {
  const query = `SELECT id, username, email, point FROM users WHERE email = '${body.email}' AND password = '${body.password}'`;
  return dbPool.execute(query);
};

const update = async (id, body) => {
  const query = `UPDATE users SET username = '${body.username}', email = '${body.email}', password = '${body.password}' WHERE id = ${id}`;
  return dbPool.execute(query);
};

const viewUser = async () => {
  const query = 'SELECT id, username, email FROM users';
  return dbPool.execute(query);
};

const getUserByEmail = async (email) => {
  const query = `SELECT id, username, email FROM users WHERE email = '${email}'`;
  return dbPool.execute(query);
};

const addPoint = async (userId, pointsToAdd) => {
  const query = `UPDATE users SET point = point + ? WHERE id = ?`;
  const values = [pointsToAdd, userId];
  return dbPool.execute(query, values);
};

module.exports = {
  register,
  login,
  update,
  viewUser,
  getUserByEmail,
  addPoint,
};
