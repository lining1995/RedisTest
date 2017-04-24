package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import until.Student;

import java.sql.Date;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Servlet implementation class AddServlet
 */
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//req.setCharacterEncoding("utf-8");

		Jedis jedis = new Jedis("127.0.0.1", 6379);
		// 获取界面上的数据
		Student student = new Student();
		//student.setName(req.getParameter("name"));
		//student.setDescription(req.getParameter("description"));
		
		student.setName(new String(req.getParameter("name").getBytes("ISO-8859-1"), "utf-8"));
		student.setDescription(new String(req.getParameter("description").getBytes("ISO-8859-1"), "utf-8"));

		
		student.setAvgscore(Integer.parseInt(req.getParameter("avgscore")));
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            student.setBirthday(new Date(simpleDateFormat.parse(req.getParameter("birthday")).getTime()));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

		Long id;
		Integer avgscore = Integer.parseInt(req.getParameter("avgscore"));
		if (req.getParameter("id") == null || req.getParameter("id").equals("")) {
			// 把系统当前毫秒为id
			id = System.currentTimeMillis();
			// 这条数据存储所有的id
			jedis.rpush("studentId", id.toString());
		} else {
			id = Long.parseLong(req.getParameter("id"));
		}
		student.setId(id);
		student.setAvgscore(avgscore);
		// 添加一个有序集合，便于之后按平均分排序
		jedis.zadd("studentAvgscore", avgscore, id.toString());
		Map<String, String> map = student.toMap();
		jedis.hmset("student:" + id, map);
		resp.sendRedirect("StudentListServlet");
	}
}
