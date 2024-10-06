const express = require('express');
const mongoose = require('mongoose');
const app = express();

const port = 3000;
const multer = require('multer');
const COMMON = require('./database/COMMON');
const SanPhamModel = require('./database/SanPhamModel');
const UserModel = require('./database/UserModel');
const GioHangModel = require('./database/GioHangModel');
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

router.get('/search/:tenSP', async (req, res) => {
    const ten = req.params.tenSP;
    try {
        let sanpham;

        if (ten) {
            sanpham = await SanPhamModel.find({ tenSP: { $regex: new RegExp(ten, "i") } });
        } else {
            sanpham = await SanPhamModel.find();
        }

        res.send(sanpham);
    } catch (error) {
        console.error('Error searching products:', error);
        res.status(500).send('Internal Server Error');
    }
});

router.get('/getSP/:id', async (req, res) => {
    try {
        await mongoose.connect(uri);
        let id = req.params.id;
        const sanpham = await SanPhamModel.findById(id);
        if (!sanpham) {
            return res.status(404).send('Sản phẩm không tồn tại');
        }
        res.send(sanpham);
    } catch (error) {
        console.error('Error fetching product by ID:', error);
        res.status(500).send('Lỗi máy chủ');
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
            const id = user._id;
            const role = user.role; // Lấy vai trò từ người dùng đã xác thực
            res.status(200).json({ message: 'Đăng nhập thành công', role: role , _id: id});
        } else {
            res.status(401).json({ message: 'Sai thông tin đăng nhập' });
        }
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Lỗi máy chủ' });
    }
});

router.delete('/del_user/:id', async (req, res)=>{
    try {
        await mongoose.connect(uri);
        let id = req.params.id;
        console.log(id);
        const result = await UserModel.deleteOne({ _id: id });
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

router.get('/list_user', async (req, res)=>{
    await mongoose.connect(uri);
    let data = await UserModel.find();
    res.send(data);
});



router.post('/addGioHang', async (req, res) => {
    try {
        await mongoose.connect(uri);
        const data = req.body;
        const newGH = new GioHangModel({
            maUser: data.maUser,
            maSP: data.maSP,
            soLuong: data.soLuong,
            trangThaiMua: data.trangThaiMua,
        })
        const result = await newGH.save();
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Thêm thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Thêm không thành công",
                "data": []
            })

        }
        
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
});

router.get('/list_gh/:id', async (req, res) => {
    const { id } = req.params;
  
    // Validate ID
    if (!mongoose.Types.ObjectId.isValid(id)) {
      return res.status(400).json({ message: 'Invalid ID' });
    }
  
    try {
      const userId = new mongoose.Types.ObjectId(id);
  
      const cartItems = await GioHangModel.find({ maUser: userId })
        .populate('maSP')
        .exec();
  
      if (cartItems.length === 0) {
        return res.status(404).json({ message: 'User not found or cart is empty' });
      }
  
      res.status(200).json(cartItems);
    } catch (error) {
      console.error('Error fetching cart items:', error);
      res.status(500).json({ message: 'Internal server error' });
    }
  });

router.delete('/del_gh/:id', async(req, res)=>{
    try {
        await mongoose.connect(uri);
        let id = req.params.id;
        console.log(id);
        const result = await GioHangModel.deleteOne({ _id: id });
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

router.post('/updateGioHang', async (req, res) => {
    try {
        await mongoose.connect(uri);
        const data = req.body;

        // Tìm sản phẩm trong giỏ hàng của người dùng với sản phẩm có ID đã có trong giỏ hàng hay không
        const existingItem = await GioHangModel.findOne({
            maUser: data.maUser,
            'maSP._id': data.maSP._id
        });

        if (existingItem) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            existingItem.soLuong = data.soLuong;  // Cập nhật số lượng sản phẩm
            await existingItem.save();  // Lưu thay đổi vào DB

            return res.json({
                "status": 200,
                "messenger": "Cập nhật giỏ hàng thành công",
                "data": existingItem
            });
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới sản phẩm vào giỏ hàng
            const newGH = new GioHangModel({
                maUser: data.maUser,
                maSP: data.maSP,
                soLuong: data.soLuong,
                trangThaiMua: data.trangThaiMua,
            });
            const result = await newGH.save();  // Lưu giỏ hàng mới vào DB

            return res.json({
                "status": 200,
                "messenger": "Thêm giỏ hàng thành công",
                "data": result
            });
        }
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
});


