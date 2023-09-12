package BackEndWouldYouRather.BackEndWouldYouRather.web.entity.converter;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.AttributeConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

/**
 * Convert comma delimited <b>string</b> to
 * <b>Set&lt;SimpleGrantedAuthority&gt;</b>,<br>
 * Convert <b>Set&lt;SimpleGrantedAuthority&gt;</b> to comma delimited
 * <b>string</b>
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public class GrantedAuthorityConverter implements AttributeConverter<Set<SimpleGrantedAuthority>, String> {
	private static final Log logger = LogFactory.getLog(GrantedAuthorityConverter.class);

	@Override
	public String convertToDatabaseColumn(Set<SimpleGrantedAuthority> attribute) {
		if (null != attribute && !attribute.isEmpty()) {
			try {
				StringBuilder sb = new StringBuilder();
				Iterator<SimpleGrantedAuthority> it = attribute.iterator();
				while (it.hasNext()) {
					sb.append(it.next().getAuthority());
					if (it.hasNext()) {
						sb.append(",");
					}
				}
				return sb.toString();
			} catch (Exception e) {
				logger.error("Could not convert set to comma delimited string: " + attribute, e);
			}
		}

		return null;
	}

	@Override
	public Set<SimpleGrantedAuthority> convertToEntityAttribute(String dbData) {
		if (StringUtils.hasText(dbData)) {
			try {

				Set<SimpleGrantedAuthority> attributeSet = null;

				String[] attributes = StringUtils.commaDelimitedListToStringArray(dbData);
				if (attributes.length > 0) {
					attributeSet = new LinkedHashSet<SimpleGrantedAuthority>();
					for (String attribute : attributes) {
						attributeSet.add(new SimpleGrantedAuthority(attribute));
					}
				}

				return attributeSet;
			} catch (Exception e) {
				logger.error("Could not convert comma delimited string to set: " + dbData, e);
			}
		}

		return null;
	}

}
