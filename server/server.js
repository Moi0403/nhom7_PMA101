const express = require('express');
const mongoose = require('mongoose');
const app = express();

const port = 3000;
const multer = require('multer');
const COMMON = require('./database/COMMON');
const SanPhamModel = require('./database/SanPhamModel');
const UserModel = require('./database/UserModel');
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

router.put('/up_sp/:id', async (req, res)=>{
    try {
        const id = req.params.id;
        const data = req.body;

        await mongoose.connect(uri);
        console.log('Kết nối DB thành công');

        const result = await SanPhamModel.findByIdAndUpdate(id, data);

        if (result) {
            let products = await SanPhamModel.find();
            console.log(products);
            res.send(products);
        } else {
            res.send('Không tìm thấy sản phẩm để cập nhật');
        }
    } catch (error) {
        console.error('Lỗi khi cập nhật:', error);
        res.send('Lỗi khi cập nhật');
    }
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

router.post('/dangki', async (req, res)=>{
    await mongoose.connect(uri);    
    let user = req.body;
    let kq = await UserModel.create(user);
    console.log(kq);

    let users = await UserModel.find();
    console.log(users);
    res.send(users);
});

router.post('/dangnhap', async (req, res) => {
    const { username, password } = req.body;

    try {
        const user = await UserModel.findOne({ username, password });
        if (user) {
            const role = user.role; // Lấy vai trò từ người dùng đã xác thực
            res.status(200).json({ message: 'Đăng nhập thành công', role: role });
        } else {
            res.status(401).json({ message: 'Sai thông tin đăng nhập' });
        }
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Lỗi máy chủ' });
    }
});



router.get('/list_user', async (req, res)=>{
    await mongoose.connect(uri);
    let data = await UserModel.find();
    res.send(data);
})
