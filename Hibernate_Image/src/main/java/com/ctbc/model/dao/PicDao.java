package com.ctbc.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.ctbc.model.vo.Pic;
import com.ctbc.util.HibernateUtil;

public class PicDao {

	public static void save() throws FileNotFoundException, IOException {
		Pic picVO = new Pic();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		FileInputStream fis = new FileInputStream("E:\\IMG_3019.JPG");
		byte[] bytes = IOUtils.toByteArray(fis);
		Blob picture = Hibernate.getLobCreator(session).createBlob(bytes);
		picVO.setPicture(picture);
		picVO.setName("IMG_3019.JPG");
		session.save(picVO);
		session.getTransaction().commit();
	}

	public static void write() throws FileNotFoundException, IOException, SQLException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Pic picVO = session.get(Pic.class, 1l);
		Blob b = picVO.getPicture();
		InputStream inputStream = b.getBinaryStream();
		FileOutputStream fos = new FileOutputStream("F:\\" + picVO.getName());
		byte[] buff = new byte[1024];
		int len;
		while ((len = b.getBinaryStream().read(buff)) != -1) {
			fos.write(buff, 0, len);
		}
		
		inputStream.close();
		fos.close();
		session.getTransaction().commit();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		save();
		write();
		HibernateUtil.getSessionFactory().close();
	}

}
