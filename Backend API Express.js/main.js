require('dotenv').config();
const express = require('express');
const userRouter = require('./routes/user.route');
require('./middlewares/passport');

const app = express();
const { PORT } = process.env;

app.use(express.json());
app.use('/user', userRouter, );

app.listen(PORT, () => {
  console.log(`Server running di port ${PORT}`);
});
