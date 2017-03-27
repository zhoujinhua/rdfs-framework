import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rdfs.framework.auth.entity.SyPermSet;
import com.rdfs.framework.auth.service.PermSetService;
import com.rdfs.framework.cache.service.CacheResourceService;
import com.rdfs.framework.core.bean.TreeDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-base.xml" })
public class AuthTest {

	@Autowired
	private CacheResourceService cacheResourceService;
	
	@Autowired
	private PermSetService permSetService;
	
	@Test
	public void test(){
		List<SyPermSet> list = permSetService.getList("from SyPermSet");
		Map<String, SortedMap<Integer, List<TreeDto>>> sortMap = cacheResourceService.getMenuTree(list);
		Map<TreeDto, List<TreeDto>> map = new HashMap<>();
		
		for(Iterator<Integer> iter = sortMap.get("0").keySet().iterator();iter.hasNext();){
			Integer key = iter.next();
			List<TreeDto> treeList = sortMap.get(0).get(key);
		}
	}
}
