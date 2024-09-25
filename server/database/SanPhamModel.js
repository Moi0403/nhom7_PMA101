const mongoose = require('mongoose');

const sanPhamSchema = new mongoose.Schema({
    anhSP: {
        type: String,
        required: true,
    },
    tenSP: {
        type: String,
        required: true, 
    },
    phanloaiSP: {
        type: String,
        required: true, 
    },
    trongluongSP: {
        type: Number,
        required: true, 
    },
    giaSP: {
        type: Number,
        required: true, 
    },
    mota: {
        type: String,
        required: true,
    }
});

// Tạo model từ schema
const SanPhamModel = mongoose.model('SanPham', sanPhamSchema);

module.exports = SanPhamModel;
