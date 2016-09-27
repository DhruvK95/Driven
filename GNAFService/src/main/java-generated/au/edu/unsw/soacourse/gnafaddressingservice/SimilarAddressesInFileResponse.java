
package au.edu.unsw.soacourse.gnafaddressingservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SimilarAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SimilarAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SimilarAddress3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "similarAddress1",
    "similarAddress2",
    "similarAddress3"
})
@XmlRootElement(name = "similarAddressesInFileResponse")
public class SimilarAddressesInFileResponse {

    @XmlElement(name = "SimilarAddress1", required = true)
    protected String similarAddress1;
    @XmlElement(name = "SimilarAddress2", required = true)
    protected String similarAddress2;
    @XmlElement(name = "SimilarAddress3", required = true)
    protected String similarAddress3;

    /**
     * Gets the value of the similarAddress1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimilarAddress1() {
        return similarAddress1;
    }

    /**
     * Sets the value of the similarAddress1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimilarAddress1(String value) {
        this.similarAddress1 = value;
    }

    /**
     * Gets the value of the similarAddress2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimilarAddress2() {
        return similarAddress2;
    }

    /**
     * Sets the value of the similarAddress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimilarAddress2(String value) {
        this.similarAddress2 = value;
    }

    /**
     * Gets the value of the similarAddress3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimilarAddress3() {
        return similarAddress3;
    }

    /**
     * Sets the value of the similarAddress3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimilarAddress3(String value) {
        this.similarAddress3 = value;
    }

}
