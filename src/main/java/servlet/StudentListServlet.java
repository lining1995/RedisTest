package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import until.Page;
import until.Student;

/**
 * Servlet implementation class StudentListServlet
 */
public class StudentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentListServlet() {
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
		req.setCharacterEncoding("utf-8");
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		// 判断是不是第一页
		String s = req.getParameter("currentPage");
		int currentPage;
		if (s != null && !s.equals("")) {
			currentPage = Integer.parseInt(req.getParameter("currentPage"));
		} else {
			currentPage = 1;
		}
		System.out.println(currentPage);
		//按平均分降序排列
		Set<String> studentIds = jedis.zrevrange("studentAvgscore", (currentPage - 1) * 10, currentPage * 10);
		System.out.println(studentIds.size());
		List<Student> students = new ArrayList<Student>();
		for (String id : studentIds) {
			Student student = new Student();
			try {
				student.fromMap(jedis.hgetAll("student:" + id));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			students.add(student);
		}
		System.out.println(students.size());
		//页对象
		Page page = new Page();
		//总条数
		int total = jedis.llen("studentId").intValue();
		//计算总页数
		if (total % 10 != 0) {
			total = total / 10 + 1;
			page.setTotal(total);
		} else {
			total = total / 10;
			page.setTotal(total);
		}
		//设置当前页
		page.setCurrentPage(currentPage);
		//计算是否还有下一页
		if (currentPage < total) {
			page.setHasNextPage(true);
			page.setNextPage(currentPage + 1);
		} else {
			page.setHasNextPage(false);
		}
		//计算是否还有上一页
		if (currentPage > 1) {
			page.setHasPrePage(true);
			page.setPrePage(currentPage - 1);
		} else {
			page.setHasPrePage(false);
		}
		req.setAttribute("list", students);
		req.setAttribute("page", page);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
