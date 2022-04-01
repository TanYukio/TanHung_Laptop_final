package com.example.tanhung_laptop.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.tanhung_laptop.Models.BinhLuan;
import com.example.tanhung_laptop.Models.LAPTOP;
import com.example.tanhung_laptop.Models.TAIKHOAN;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public void UPDATE_SOLUONG(int IDLT,int Soluong)
    {
        QueryData("UPDATE " + "LAPTOP" + " SET "
                + "SOLUONG" + " = "+"SOLUONG" + " - " + Soluong +
                " WHERE " + "IDLT" + " = " + IDLT)
        ;
    }
    public boolean SPChuaCoTrongGH(int IDTK,int IDSP){
        Cursor tro = GetData("SELECT * FROM GIOHANG WHERE IDTK = " + IDTK + " AND IDSP = " + IDSP );
        while (tro.moveToNext()) {
            return false;
        }
        return true;
    }
    public void SPGH(int IDTK,byte[] hinh, int IDSP, String TenSP, int Soluong, int tongtien){
        if(SPChuaCoTrongGH(IDTK, IDSP)){
            QueryData("INSERT INTO " + "GIOHANG" +
                    " ( "
                    + "IDTK" + " , "
                    + "HINHANH" + " , "
                    + "IDSP" + " , "
                    + "TENSP" + " , "
                    + "SOLUONG" + " , "
                    + "TONGTIEN"
                    + " ) VALUES ( " + IDTK +" , " + null + " , " + IDSP+" , '" + TenSP + "' , " + Soluong + " , "
                    + tongtien + " ) ");
            //------------------------------

            //------------------------------

            SQLiteDatabase database = getWritableDatabase();
            String sql = "UPDATE GIOHANG SET HinhAnh = ? WHERE IDTK="+ IDTK + " AND IDSP=" + IDSP ;
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindBlob(1,hinh);
            statement.executeInsert();



        }
        else {
            QueryData("UPDATE " + "GIOHANG" + " SET "
                    + "SOLUONG" + " = "+"SOLUONG" + " + " + Soluong + " , "
                    + "TONGTIEN" + " = " + "TONGTIEN" + " + " + tongtien
                    + " WHERE " + "IDTK" + " = " + IDTK+ " AND "
                    + "IDSP" + " = " + IDSP)
            ;
            ;
        }
    }
    public void ThemBL(int IDTK, int IDSP, String NOIDUNG, String THOIGIAN){
        QueryData("INSERT INTO BINHLUAN (IDTK,IDSP,NOIDUNG,THOIGIAN) VALUES (" +IDTK + ",'" +
                IDSP + "','" + NOIDUNG+"','" + THOIGIAN + "')");
    }
    public void DELETE_SANPHAM(int IDLT){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM LAPTOP WHERE IDLT = "+ IDLT  ;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.executeInsert();
    }
    public void DELETE_TAIKHOAN(int IDTAIKHOAN){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM TAIKHOAN WHERE IDTAIKHOAN = "+ IDTAIKHOAN  ;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.executeInsert();
    }
    public LAPTOP laptop(int IDLT){
        Cursor cursor = GetData("SELECT * FROM LAPTOP WHERE IDLT = " + IDLT );
        while (cursor.moveToNext()) {
            return new LAPTOP(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            );

        }
        return null;
    }
    public void UPDATE_DOAN(String TENLAPTOP,byte[] hinh,int SOLUONG,int  GIASP,int IDNSX,int LTMOI, int IDLT ){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENLAPTOP", TENLAPTOP);
        values.put("GIASP", GIASP);
        values.put("SOLUONG", SOLUONG);
        values.put("IDNSX", IDNSX);
        values.put("LTMOI", LTMOI);

        sqLiteDatabase.update("LAPTOP",values,"IDLT =" + IDLT,null);


        String sql = "UPDATE LAPTOP SET HINHANH = ? WHERE IDLT="+ IDLT ;
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,hinh);
        statement.executeInsert();
    }
    public void UPDATE_TAIKHOAN(int IDTAIKHOAN,String TENTAIKHOAN, String MATKHAU, int SDT,String EMAIL, String NGAYSINH,
                                int QUYENTK, String DIACHI, byte[] HINH){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENTAIKHOAN", TENTAIKHOAN);
        values.put("MATKHAU", MATKHAU);
        values.put("SDT", SDT);
        values.put("EMAIL", EMAIL);
        values.put("NGAYSINH", NGAYSINH);
        values.put("QUYENTK", QUYENTK);
        values.put("DIACHI", DIACHI);

        sqLiteDatabase.update("TAIKHOAN",values,"IDTAIKHOAN =" + IDTAIKHOAN,null);


        String sql = "UPDATE TAIKHOAN SET HINHANH = ? WHERE IDTAIKHOAN="+ IDTAIKHOAN ;
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,HINH);
        statement.executeInsert();
    }
    public void INSERT_DOAN(String TENLAPTOP,byte[] hinh,int SOLUONG,int  GIASP,int IDNSX,int LTMOI ) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put("TENLAPTOP", TENLAPTOP);
        cv.put("GIASP", GIASP);
        cv.put("SOLUONG", SOLUONG);
        cv.put("IDNSX", IDNSX);
        cv.put("LTMOI", LTMOI);
        cv.put("HINHANH", hinh);
        database.insert( "LAPTOP", null, cv );

    }
    public void INSERT_GOPY(String tentaikhoan, int sdt, String noidung){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO GOPY VALUES(null,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, tentaikhoan);
        statement.bindDouble(2, sdt);
        statement.bindString(3, noidung);

        statement.executeInsert();
    }
    public ArrayList<BinhLuan> LayBinhLuan(int IDSP){
        ArrayList<BinhLuan> list = new ArrayList<>();
        Cursor tro = GetData("SELECT A.IDTK,B.HINHANH,A.NOIDUNG,A.THOIGIAN FROM BINHLUAN A,TAIKHOAN B WHERE A.IDTK = B.IDTAIKHOAN AND A.IDSP ='" + IDSP +"'");
        while (tro.moveToNext()){
            list.add(new BinhLuan(
                    tro.getInt(0),
                    tro.getBlob(1),
                    tro.getString(2),
                    tro.getString(3)
            ));
        }

        return list;
    }
    public void DELETE_DOAN(int IDSP, int IDTK){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM GIOHANG WHERE IDSP = "+ IDSP + " AND IDTK= " + IDTK  ;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.executeInsert();
    }
    public void DELETE_GIOHANG(int IDTK){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM GIOHANG WHERE IDTK = "+ IDTK ;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.executeInsert();
    }
    public void INSERT_HOADON(int TONGTIEN, int IDCTHOADON, String GHICHU, String DIACHI, int IDTK)
    {
        QueryData("INSERT INTO " + "HOADON" +
                " ( "
                + "TONGTIEN" + " , "
                + "IDCTHOADON" + " , "
                + "GHICHU"+ " , "
                + "DIACHI" + " , "
                + "IDTAIKHOAN" +
                " ) VALUES ( " + TONGTIEN + " , " + IDCTHOADON + " , '" + GHICHU + "' , '" + DIACHI + "' , " + IDTK + " ) ");
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    public void INSERT_CTHOADON(int IDCTHOADON,int IDTK, int IDSP, String TenSP, int Soluong, int THANHTIEN)
    {
        QueryData("INSERT INTO " + "CHITIETHOADON " +
                " ( "
                + "IDCTHOADON" + " , "
                + "IDSANPHAM" + " , "
                + "IDTAIKHOAN"+ " , "
                + "TENSANPHAM" + " , "
                + "SOLUONG" + " , "
                + "THANHTIEN"
                + " ) VALUES ( " + IDCTHOADON +" , " + IDSP + " , " + IDTK+" , '" + TenSP + "' , " + Soluong + " , "
                + THANHTIEN + " ) ");
    }
    public boolean HoaDonChuaCoTrongHD(){
        Cursor tro = GetData("SELECT IDCTHOADON FROM CHITIETHOADON " );
        while (tro.moveToNext()) {
            return false;
            // DA CO TON TAI TRONG HOA DON
        }
        return true;
        // CHUA ID HOA DON
    }
    public boolean tontaitaikhoan(String tk, String mk){
        Cursor cursor = GetData("SELECT * FROM TAIKHOAN WHERE TENTAIKHOAN = '" + tk +"' AND MATKHAU =" + mk);
        //di chuyển dữ liệu giữa các dòng riêng biệt bạn có thể sử hương thức dùng để kiểm tra xem đã kết thúc câu lệnh truy vấn đã đạt được
        while (cursor.moveToNext()){
            return false;
        }
        return true;
    }
    public long themtaikhoan(TAIKHOAN taikhoan){
        // SQLiteDatabase là lớp cơ sở để làm việc với cơ sở dữ liệu SQLite trong Android và nó cung cấp
        //có chứa các phương thức tạo, xóa, thực thi các lệnh SQL, nó sẽ được sử dụng để insert các giá trị từ object
        SQLiteDatabase database = getWritableDatabase();
        //value là giá trị của bản ghi của cột này. ContentValues sử dụng để insert và cập nhật dữ liệu
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENTAIKHOAN", taikhoan.getTENTAIKHOAN());
        contentValues.put("MATKHAU", taikhoan.getMATKHAU());
        contentValues.put("SDT", taikhoan.getSDT());
        contentValues.put("EMAIL", taikhoan.getEMAIL());
        contentValues.put("QUYENTK",1);
        long kiemtra = database.insert("TAIKHOAN", null, contentValues);
        return kiemtra;
    }

    public void UPDATE_IMAGE_TK(int IDTAIKHOAN, byte[] hinh){
        String sql = "UPDATE TAIKHOAN SET HINHANH = ? WHERE IDTAIKHOAN="+ IDTAIKHOAN ;
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,hinh);
        statement.executeInsert();
    }
    public void CapNhatTaiKhoan(int IDTAIKHOAN, int SDT, String EMAIL, String DIACHI){
        QueryData("UPDATE TAIKHOAN SET SDT = '" + SDT + "', EMAIL = '" + EMAIL +
                "' , DIACHI = '" + DIACHI + "' WHERE IDTAIKHOAN = '" + IDTAIKHOAN +"'");
    }
    public TAIKHOAN Load(int IDTK)
    {
        Cursor cursor = GetData("SELECT * FROM TAIKHOAN WHERE IDTAIKHOAN = " + IDTK );
        while (cursor.moveToNext()) {
            return new TAIKHOAN(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getBlob(8)
            );
        }
        return null;

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
