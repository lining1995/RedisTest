package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import until.Student;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class EditServlet
 */
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditServlet() {
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
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		Map<String, String> map = new HashMap<String, String>();
		Student student = new Student();
		String id = req.getParameter("id");
		try {
			//如果有id传进来说明是修改操作，那么就先查出来原始数据,因为与添加公用了一个界面
			student.fromMap(jedis.hgetAll("student:" + id));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		req.setAttribute("student", student);
		req.getRequestDispatcher("add.jsp").forward(req, resp);
	}

}
