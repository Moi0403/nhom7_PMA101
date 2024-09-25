const express = require('express');
const mongoose = require('mongoose');
const app = express();

const port = 3000;
const multer = require('multer');
const COMMON = require('./database/COMMON');
const SanPhamModel = require('./database/SanPhamModel');
const uri = COMMON.uri;

const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true}));

app.listen(port, ()=>{
    console.log('Server run 3000');
});

const router = express.Router();
app.use('/api', router);


mongoose.connect(uri).then(() => {
    console.log('Kết nối MongoDB thành công');
}).catch(err => {
    console.error('Lỗi kết nối MongoDB:', err);
});

app.get('/list_sp', async (req, res) => {
    await mongoose.connect(uri);
    let data = await SanPhamModel.find();
    console.log(data);
    res.send(data);
});

router.get('/list_sp', async (req, res) => {
    await mongoose.connect(uri);
    let data = await SanPhamModel.find();
    res.send(data);
});

router.post('/add_sp', async (req, res) => {
    await mongoose.connect(uri);
    let data = req.body;
    let kq = await SanPhamModel.create(data);
    console.log(kq);
    let sp = await SanPhamModel.find();
    console.log(sp);
    res.send(sp);
});

router.delete('/del_sp/:id', async (req, res) => {
    try {
        await mongoose.connect(uri);
        let id = req.params.id;
        console.log(id);
        const result = await SanPhamModel.deleteOne({ _id: id });
        if (result) {
            console.log('Xoa thanh cong');
        } else {
            res.send('Xóa không thành công');
        }
    } catch (error) {
        console.error('Lỗi khi xóa:', error);
        res.send('Lỗi khi xóa');
    }
});

