package idv.hsiehpinghan.hanlpassistant.segment.assistant;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

@Component
public class OrganizationRecognizeSegmentAssistant {
	private Segment segment = null;

	public OrganizationRecognizeSegmentAssistant() {
		segment = HanLP.newSegment().enableOrganizationRecognize(true);
	}

	public List<Term> segment(String str) {
		return segment.seg(str);
	}
}
