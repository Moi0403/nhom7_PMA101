const express = require('express');
const mongoose = require('mongoose');
const app = express();

const port = 3000;
const multer = require('multer');
const COMMON = require('./database/COMMON');
const SanPhamModel = require('./database/SanPhamModel');
const UserModel = require('./database/UserModel');
const GioHangModel = require('./database/GioHangModel');
const ThanhToanModel = require('./database/ThanhToanModel');

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

router.put('/up_user/:id', async (req, res)=>{
    try {
        const id = req.params.id;
        const data = req.body;

        await mongoose.connect(uri);
        console.log('Kết nối DB thành công');

        const result = await UserModel.findByIdAndUpdate(id, data);

        if (result) {
            let products = await UserModel.find();
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

router.get('/list_user', async (req, res)=>{
    await mongoose.connect(uri);
    let data = await UserModel.find();
    res.send(data);
});
router.get('/list_user/:id', async(req, res)=>{
    try {
        await mongoose.connect(uri);
        let id = req.params.id;
        const sanpham = await UserModel.findById(id);
        if (!sanpham) {
            return res.status(404).send('Sản phẩm không tồn tại');
        }
        res.send(sanpham);
    } catch (error) {
        console.error('Error fetching product by ID:', error);
        res.status(500).send('Lỗi máy chủ');
    }
});


router.post('/addGioHang', async (req, res) => {
    try {
        await mongoose.connect(uri);
        const data = req.body;
        const newGH = new GioHangModel({
            maUser: data.maUser,
            maSP: data.maSP,
            soLuong: data.soLuong,
            giaGH: data.giaGH,
            trangThaiMua: data.trangThaiMua,
            tongtien: data.tongtien,
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
  
    console.log("Received ID from Android:", id); // Thêm log để kiểm tra ID nhận được
  
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

router.put('/up_gh/:id', async (req, res) => {
    try {
        await mongoose.connect(uri);
        const gioHangId = req.params.id; 
        const data = req.body; 

       
        const updatedGH = await GioHangModel.findByIdAndUpdate(
            gioHangId,
            {
                $set: {
                    maUser: data.maUser,
                    maSP: data.maSP,
                    soLuong: data.soLuong,
                    giaGH: data.giaGH,
                    trangThaiMua: data.trangThaiMua,
                    tongtien: data.tongtien,
                }
            },
            { new: true }
        );

        if (updatedGH) {
            res.json({
                "status": 200,
                "message": "Cập nhật thành công",
                "data": updatedGH
            });
        } else {
            res.json({
                "status": 400,
                "message": "Không tìm thấy giỏ hàng để cập nhật",
                "data": []
            });
        }
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
});

router.post('/add_tt', async (req, res) => {
    try {
        const { maUser, tenSP, anhSP, giaSP, soLuong, trangthaiTT } = req.body;

        if (!maUser || !tenSP || !anhSP || !giaSP || !soLuong || !trangthaiTT) {
            return res.status(400).json({ message: 'Thiếu thông tin bắt buộc' });
        }

        const thanhToanMoi = new ThanhToanModel({
            maUser,
            tenSP,
            anhSP,
            giaSP,
            soLuong,
            trangthaiTT
        });

        await thanhToanMoi.save();
        res.status(201).json({ message: 'Thanh toán đã được thêm thành công', thanhToan: thanhToanMoi });
    } catch (error) {
        console.error('Lỗi khi thêm thanh toán:', error);
        res.status(500).json({ message: 'Đã xảy ra lỗi khi thêm thanh toán', error: error.message });
    }
});
router.get('/thanhtoan/:maUser', async (req, res) => {
    try {
        // Lấy _id của người dùng từ URL params
        const { maUser } = req.params;

        // Tìm tất cả các thanh toán của người dùng dựa trên maUser (_id)
        const thanhToanList = await ThanhToanModel.find({ maUser });

        // Kiểm tra nếu không có dữ liệu thanh toán nào
        if (thanhToanList.length === 0) {
            return res.status(404).json({ message: 'Không tìm thấy thanh toán cho người dùng này' });
        }

        // Trả về danh sách thanh toán
        res.status(200).json(thanhToanList);
    } catch (error) {
        console.error('Lỗi khi lấy thanh toán:', error);
        res.status(500).json({ message: 'Đã xảy ra lỗi khi lấy thanh toán', error: error.message });
    }
});





