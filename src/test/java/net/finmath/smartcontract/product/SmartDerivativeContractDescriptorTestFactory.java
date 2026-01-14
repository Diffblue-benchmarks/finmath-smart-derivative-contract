package net.finmath.smartcontract.product;

import com.diffblue.cover.annotations.InterestingTestFactory;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor.Party;

/**
 * Factory class to provide test instances for SmartDerivativeContractDescriptor and related classes.
 */
public class SmartDerivativeContractDescriptorTestFactory {

    /**
     * Creates a Party instance with valid parameters.
     * This factory helps test Party methods without complex setup.
     */
    @InterestingTestFactory
    public static Party createParty() {
        return new Party(
            "party-123",
            "Test Party Name",
            "https://example.com/party/123",
            "0x1234567890abcdef1234567890abcdef12345678"
        );
    }

    /**
     * Creates a Party instance with minimal valid parameters.
     */
    @InterestingTestFactory
    public static Party createPartyWithMinimalData() {
        return new Party("id1", "Name", null, null);
    }

    /**
     * Creates a Party instance with all null optional fields.
     */
    @InterestingTestFactory
    public static Party createPartyWithNulls() {
        return new Party(null, null, null, null);
    }
}
