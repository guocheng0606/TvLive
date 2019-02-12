package com.android.guocheng.tvlive.db;

import com.android.guocheng.tvlive.manager.GreenDaoManager;
import com.android.guocheng.tvlive.model.custom.CustomSource;
import com.android.guocheng.tvlive.model.custom.CustomSourceDao;

import java.util.List;

/**
 */

public class CustomDbTool {

    //添加一个
    public static void add(CustomSource customSource){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.insert(customSource);
    }

    //批量添加
    public static void addList(List<CustomSource> list){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.insertInTx(list);
    }

    //根据实体删除
    public static void delete(CustomSource info){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.delete(info);
    }

    //根据实体批量删除
    public static void deleteList(List<CustomSource> list){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.deleteInTx(list);
    }

    //根据id删除
    public static void deleteById(Long id){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.deleteByKey(id);
    }

    //根据id批量删除
    public static void deleteListById(List<Long> list){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.deleteByKeyInTx(list);
    }

    //删除全部
    public static void deleteAll(){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.deleteAll();
    }

    //修改一个
    public static void update(CustomSource info){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.update(info);
    }

    //批量修改
    public static void updateList(List<CustomSource> list){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        dao.updateInTx(list);
    }


    //查询全部
    public static List<CustomSource> queryAll(){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        List<CustomSource> list = dao.loadAll();
        return list;
    }

    /*public static List<CustomInfo> queryFavoriteSong(){
        SongInfoDao songInfoDao = GreenDaoManager.getInstance().getSession().getSongInfoDao();
        List<SongInfo> list = songInfoDao.queryBuilder().where(SongInfoDao.Properties.Favorite.eq(true)).build().list();
        return list;
    }*/

    public static List<CustomSource> queryByName(String name){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        List<CustomSource> list = dao.queryBuilder().where(CustomSourceDao.Properties.Name.eq(name)).build().list();
        return list;
    }

    public static CustomSource queryById(Long id){
        CustomSourceDao dao = GreenDaoManager.getInstance().getSession().getCustomSourceDao();
        return dao.load(id);
    }

}
