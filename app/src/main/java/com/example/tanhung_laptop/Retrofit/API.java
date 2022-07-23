package com.example.tanhung_laptop.Retrofit;

import com.example.tanhung_laptop.ModelsPHP.BinhluanModel;
import com.example.tanhung_laptop.ModelsPHP.CthoadonModel;
import com.example.tanhung_laptop.ModelsPHP.GiohangModel;
import com.example.tanhung_laptop.ModelsPHP.GopyModel;
import com.example.tanhung_laptop.ModelsPHP.HoadonModel;
import com.example.tanhung_laptop.ModelsPHP.DoubleModel;
import com.example.tanhung_laptop.ModelsPHP.LaptopModel;
import com.example.tanhung_laptop.ModelsPHP.MessageModel;
import com.example.tanhung_laptop.ModelsPHP.StringModel;
import com.example.tanhung_laptop.ModelsPHP.TaiKhoanModel;
import com.example.tanhung_laptop.ModelsPHP.TheLoaiModel;
//import com.example.tanhung_laptop.ModelsPHP.TestModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    // GET

    // Admin


    @GET("layspmoi.php")
    Observable<LaptopModel> layspmoi();

    @GET("layhetgopy.php")
    Observable<GopyModel> layhetGopy();

    @GET("layhetsp.php")
    Observable<LaptopModel> layhetSp();

    @GET("layhettk.php")
    Observable<TaiKhoanModel> layhetTk();
    @GET("layhettktk.php")
    Observable<TheLoaiModel> layhetTkTK();
    //User get
    @GET("layidcthd.php")
    Observable<DoubleModel> layidcthd();

    @GET("laysllt.php")
    Observable<DoubleModel> laysllt();

    @GET("layltduoi50.php")
    Observable<LaptopModel> layltduoi50();

    @GET("layhettongtienhd.php")
    Observable<DoubleModel> layhettongtienhd();
    // POST
//    @POST("t.php")
//    @FormUrlEncoded
//    Observable<TestModel> test(
//            @Field("a") Integer a
//    );
    @POST("themlt.php")
    @FormUrlEncoded
    Observable<MessageModel> themlt(
            @Field("hinhanh") String hinhanh,
            @Field("tenlaptop") String tenlaptop,
            @Field("giasp") Integer giasp,
            @Field("soluong") Integer soluong,
            @Field("motasp") String motasp,
            @Field("idnsx") Integer idnsx,
            @Field("ltmoi") Integer ltmoi
    );

    @POST("xoalt.php")
    @FormUrlEncoded
    Observable<MessageModel> xoalt(
            @Field("idlt") Integer idlt
    );

    @POST("xoatk.php")
    @FormUrlEncoded
    Observable<MessageModel> xoatk(
            @Field("idtaikhoan") Integer idtaikhoan
    );

    @POST("capnhatlt.php")
    @FormUrlEncoded
    Observable<MessageModel> capNhatlt(
            @Field("idlt") Integer idlt,
            @Field("hinhanh") String hinhanh,
            @Field("tenlaptop") String tenlaptop,
            @Field("giasp") Integer giasp,
            @Field("soluong") Integer soluong,
            @Field("motasp") String motasp,
            @Field("idnsx") Integer idnsx,
            @Field("ltmoi") Integer ltmoi
    );

    @POST("doitk.php")
    @FormUrlEncoded
    Observable<TaiKhoanModel> doitk(
            @Field("idtaikhoan") Integer idtaikhoan,
            @Field("tentaikhoan") String tentaikhoan,
            @Field("matkhau") String matkhau,
            @Field("sdt") String sdt,
            @Field("email") String email,
            @Field("ngaysinh") String ngaysinh,
            @Field("quyentk") Integer quyentk,
            @Field("diachi") String diachi,
            @Field("hinhanh") String hinhanh
    );
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<TaiKhoanModel> dangNhap(
            @Field("tentaikhoan") String tentaikhoan,
            @Field("matkhau") String matkhau
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<MessageModel> dangKi(
            @Field("tentaikhoan") String tentaikhoan,
            @Field("matkhau") String matkhau,
            @Field("sdt") String sdt,
            @Field("email") String email,
            //@Field("ngaysinh") String ngaysinh,
            @Field("quyentk") Integer quyentk,
            //@Field("diachi") String diachi
            @Field("hinhanh") String hinhanh
    );


    @POST("gopy.php")
    @FormUrlEncoded
    Observable<MessageModel> gopY(
            @Field("tentaikhoan") String tentaikhoan,
            @Field("sdt") String sdt,
            @Field("noidung") String noidung
    );

    @POST("gettk.php")
    @FormUrlEncoded
    Observable<TaiKhoanModel> thongTinTaiKhoan(
            @Field("idtaikhoan") Integer idtaikhoan
    );
    @POST("layhinhanh.php")
    @FormUrlEncoded
    Observable<StringModel> layhinhanh(
            @Field("idlt") Integer idlt
    );
    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<LaptopModel> TimKiem(
            @Field("search") String search
    );

    @POST("layspnsx.php")
    @FormUrlEncoded
    Observable<LaptopModel> laySpnsx(
            @Field("idnsx") Integer idnsx
    );

    @POST("laysp.php")
    @FormUrlEncoded
    Observable<LaptopModel> laySp(
            @Field("idlt") Integer idlt
    );

    @POST("laybl.php")
    @FormUrlEncoded
    Observable<BinhluanModel> layBl(
            @Field("idlt") Integer idlt
    );

    @POST("laygh.php")
    @FormUrlEncoded
    Observable<GiohangModel> layGh(
            @Field("idtk") Integer idtk
    );

    @POST("layhd.php")
    @FormUrlEncoded
    Observable<HoadonModel> layHd(
            @Field("idtaikhoan") Integer idtaikhoan
    );


    @POST("laycthd.php")
    @FormUrlEncoded
    Observable<CthoadonModel> layCthd(
            @Field("idhoadon") Integer idhoadon
    );

    // Admin

    @POST("themsp.php")
    @FormUrlEncoded
    Observable<TaiKhoanModel> themSp(
            @Field("hinhanh") String hinhanh,
            @Field("tenlaptop") String tenlaptop,
            @Field("giasp") Integer giasp,
            @Field("soluong") Integer soluong,
            @Field("motasp") String motasp,
            @Field("idnsx") Integer idnsx,
            @Field("ltmoi") Integer ltmoi
    );

    @POST("suasp.php")
    @FormUrlEncoded
    Observable<TaiKhoanModel> suaSp(
            @Field("idlt") String idlt,
            @Field("hinhanh") String hinhanh,
            @Field("tenlaptop") String tenlaptop,
            @Field("giasp") Integer giasp,
            @Field("soluong") Integer soluong,
            @Field("motasp") String motasp,
            @Field("idnsx") Integer idnsx,
            @Field("ltmoi") Integer ltmoi
    );

    @POST("suatk.php")
    @FormUrlEncoded
    Observable<TaiKhoanModel> suaTaiKhoan(
            @Field("idtaikhoan") Integer idtaikhoan,
            @Field("tentaikhoan") String tentaikhoan,
            @Field("matkhau") String matkhau,
            @Field("sdt") String sdt,
            @Field("email") String email,
            @Field("ngaysinh") String ngaysinh,
            @Field("quyentk") Integer quyentk,
            @Field("hinhanh") String hinhanh
    );
    @POST("doithongtintk.php")
    @FormUrlEncoded
    Observable<TaiKhoanModel> capnhatthongtin(
            @Field("idtaikhoan") Integer idtaikhoan,
            @Field("sdt") String sdt,
            @Field("email") String email,
            @Field("ngaysinh") String ngaysinh,
            @Field("diachi") String diachi
    );

    @POST("doimatkhau.php")
    @FormUrlEncoded
    Observable<MessageModel> doimatkhau(
            @Field("idtaikhoan") Integer idtaikhoan,
            @Field("mkcu") String mkcu,
            @Field("mkmoi") String mkmoi
    );

    @POST("doiavata.php")
    @FormUrlEncoded
    Observable<MessageModel> doiavata(
            @Field("idtaikhoan") Integer idtaikhoan,
            @Field("hinhanh") String hinhanh
    );
    @POST("themspgh.php")
    @FormUrlEncoded
    Observable<LaptopModel> themspgh(
            @Field("idtk") Integer idtk,
            @Field("idlt") Integer idlt,
            @Field("tenlaptop") String tenlaptop,
            @Field("soluong") Integer soluong,
            @Field("tongtien") Integer tongtien
    );
    @POST("laygh.php")
    @FormUrlEncoded
    Observable<GiohangModel> laygh(
            @Field("idtk") Integer idtaikhoan
    );
    @POST("themhd.php")
    @FormUrlEncoded
    Observable<LaptopModel> themhd(
            @Field("idtaikhoan") Integer idtaikhoan,
            @Field("thoigian") String thoigian,
            @Field("tongtien") double tongtien,
            @Field("ghichu") String ghichu,
            @Field("diachi") String diachi
    );

    @POST("themcthd.php")
    @FormUrlEncoded
    Observable<MessageModel> themcthd(
            @Field("idtaikhoan") Integer idtaikhoan,
            @Field("thoigian") String thoigian,
            @Field("idlt") Integer idlt,
            @Field("tenlaptop") String tenlaptop,
            @Field("soluong") Integer soluong,
            @Field("thanhtien") Integer thanhtien
    );

    @POST("thembl.php")
    @FormUrlEncoded
    Observable<MessageModel> thembl(
            @Field("idtaikhoan") Integer idtaikhoan,
            @Field("idlt") Integer idlt,
            @Field("thoigian") String thoigian,
            @Field("noidung") String noidung
    );

    @POST("laytongtiengh.php")
    @FormUrlEncoded
    Observable<DoubleModel> laytongtien(
            @Field("idtk") Integer idtk
    );

    @POST("laytongtienhd.php")
    @FormUrlEncoded
    Observable<DoubleModel> laytongtienhd(
            @Field("idtaikhoan") Integer idtaikhoan
    );

    @POST("xoahetgh.php")
    @FormUrlEncoded
    Observable<MessageModel> xoahetgh(
            @Field("idtk") Integer idtk
    );
    @POST("xoagh.php")
    @FormUrlEncoded
    Observable<MessageModel> xoagh(
            @Field("idtk") Integer idtk,
            @Field("idlt") Integer idlt
    );

}
