import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface inter {
	public List<String> in_hantei(String workId,String workName) ;
	public int yarukoto();
	public void ikisaki(HttpServletRequest request,HttpServletResponse response,String No,int add);
	}




//http://localhost:8080/11-14_yasuda/No11.jsp
