
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
 *         &lt;element name="SuburbName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="StateName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "suburbName",
    "stateName"
})
@XmlRootElement(name = "returnPostcodeInfoRequest")
public class ReturnPostcodeInfoRequest {

    @XmlElement(name = "SuburbName", required = true)
    protected String suburbName;
    @XmlElement(name = "StateName", required = true)
    protected String stateName;

    /**
     * Gets the value of the suburbName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuburbName() {
        return suburbName;
    }

    /**
     * Sets the value of the suburbName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuburbName(String value) {
        this.suburbName = value;
    }

    /**
     * Gets the value of the stateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * Sets the value of the stateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateName(String value) {
        this.stateName = value;
    }

}
