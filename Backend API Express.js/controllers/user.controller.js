const jwt = require('jsonwebtoken');
const userService = require('../services/user.service');

const register = async (req, res) => {
  const { body } = req;

  if (!body.username || !body.email || !body.password) {
    return res.status(400).json({
      status: 'fail',
      message: 'data anda tidak sesuai',
    });
  }

  try {
    const user = await userService.getUserByEmail(body.email);
    console.log(user);

    if (user[0][0]) {
      return res.status(409).json({
        status: 'fail',
        message: 'email yang digunakan sudah terdaftar',
      });
    }

    await userService.register(body);

    return res.status(201).json({
      status: 'success',
      mesaage: 'data berhasil disimpan',
      data: body,
    });
  } catch (error) {
    console.log(error);
    return res.status(500).json({
      status: 'fail',
      mesaage: 'gagal menyimpan data anda',
    });
  }
};

const login = async (req, res) => {
  const { body } = req;

  if (!body.email || !body.password) {
    return res.status(400).json({
      status: 'fail',
      message: 'email dan password tidak boleh kosong!',
    });
  }

  try {
    const user = await userService.login(body);

    if (!user) {
      return res.status(400).json({
        status: 'fail',
        message: 'email dan password salah',
      });
    }

    const dataUser = user[0][0];

    const jwtToken = jwt.sign(
      { id: dataUser.id, email: dataUser.email },
      process.env.JWT_SECRET,
    );

    return res.status(200).json({
      status: 'success',
      mesaage: 'login berhasil',
      token: jwtToken,
      data: dataUser,
    });
  } catch (error) {
    return res.status(500).json({
      status: 'fail',
      message: 'login gagal',
    });
  }
};

const update = async (req, res) => {
  const { id } = req.user[0][0];
  const { body } = req;

  if (!body.username || !body.email || !body.password) {
    return res.status(400).json({
      status: 'fail',
      message: 'data anda tidak sesuai',
    });
  }

  try {
    await userService.update(id, body);

    return res.status(200).json({
      status: 'success',
      message: 'data berhasil diperbarui',
    });
  } catch (error) {
    return res.status(500).json({
      status: 'fail',
      message: 'gagal perabarui data',
    });
  }
};

const viewUser = async (req, res) => {
  try {
    const [user] = await userService.viewUser();

    return res.status(200).json({
      status: 'success',
      message: 'data berhasil ditampilkan',
      data: user,
    });
  } catch (error) {
    return res.status(500).json({
      status: 'fail',
      mesaage: 'gagal menampilkan data',
    });
  }
};

module.exports = {
  register,
  login,
  update,
  viewUser,
};