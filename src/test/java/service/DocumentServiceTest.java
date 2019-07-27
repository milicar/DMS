package service;

import com.mr.dao.TagDAO;
import com.mr.domain.Document;
import com.mr.domain.DocumentTag;
import com.mr.service.impl.DocumentServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceTest {

    
    @Mock
    TagDAO tagDAO;
    @InjectMocks @Spy
    DocumentServiceImpl documentService;
    
    @Test
    public void shouldAddTagIfItIsNotAlreadyInTagList() {
        Document document = new Document();
        List<DocumentTag> tagList = new ArrayList<>();
        doReturn(tagList).when(documentService).findTagsFor(document);
        
        documentService.addTag("value1", document);
        
        verify(tagDAO).save(any());
    }
}
