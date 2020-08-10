package dao;

import entity.Music;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MusicDaoTest {

    @Test
    public void findMusic() {
        //查找所有音乐显示在list中
        List<Music> musicList = new ArrayList<>();
        MusicDao musicDao = new MusicDao();
        musicList = musicDao.findMusic();
        System.out.println(musicList);

    }

    @Test
    public void findMusicById() {
        //查找id是25的音乐 数据库中有
        MusicDao musicDao = new MusicDao();
        Music music = new Music();
        music = musicDao.findMusicById(25);
        System.out.println(music.getTitle());
        //数据库中没有呢
    }

    @Test
    public void ifMusic() {
        //根据关键字查询歌单
        MusicDao musicDao = new MusicDao();
        List<Music> musicList = new ArrayList<>();
         musicList=  musicDao.ifMusic("稻");
        System.out.println(musicList);

    }

    @Test
    public void insert() {
        //插入这首歌 数据库中没有
        MusicDao musicDao = new MusicDao();
        musicDao.insert("稻香","周杰伦","2020-08-09","music/稻香",5);
    }

    @Test
    public void deleteMusicById() {
        //根据id删除音乐
        MusicDao musicDao = new MusicDao();
       int flg = musicDao.deleteMusicById(25);
        System.out.println(flg);

    }

    @Test
    public void findLoveMusicOnDelete() {
        //看lovemusic表中是否有要删除的这个音乐
        MusicDao musicDao = new MusicDao();
        boolean flg = musicDao.findLoveMusicOnDelete(25);
        System.out.println(flg);//此处测试的是没有的


    }

    @Test
    public void removeLoveMusicOnDelete() {
        //当删除服务器上的音乐时,同时在我喜欢的数据库中进行删除
        MusicDao musicDao = new MusicDao();
        int  flg =musicDao.removeLoveMusicOnDelete(25);
        System.out.println(flg);//没有这首歌删除不掉所以是0
    }

    @Test
    public void insertLoveMusic() {
        MusicDao musicDao = new MusicDao();
        boolean flg =musicDao.insertLoveMusic(6,20);
        System.out.println(flg);//插入成功(用户存在 音乐存在)


    }

    @Test
    public void removeLoveMusic() {
        MusicDao musicDao = new MusicDao();
            int flg =musicDao.removeLoveMusic(6,20);
        System.out.println(flg);//1证明移除成功 用户存在 音乐存在 并且用户喜欢这个音乐
    }

    @Test
    public void findMusicByMusicId() {
        //判断音乐是否在lovemusic表中存在
        MusicDao musicDao = new MusicDao();
      boolean flg = musicDao.findMusicByMusicId(6,20);
        System.out.println(flg);//不存在 用户喜欢列表没有这个歌
    }


    @Test
    public void findLoveMusic() {
        //查询用户喜欢的全部歌单
        List<Music> musicList = new ArrayList<>();
        MusicDao musicDao = new MusicDao();
       musicList = musicDao.findLoveMusic(1);
        System.out.println(musicList.size());//用户存在 看有几首喜欢的

    }

    @Test
    public void ifMusicLove() {
        //根据关键字查询喜欢的歌单
        List<Music> musicList = new ArrayList<>();
        MusicDao musicDao = new MusicDao();
        musicList = musicDao.ifMusicLove("Lover",20);
        System.out.println(musicList);
    }
}