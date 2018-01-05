<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObjectModel;

class CustomerModel extends JsonObjectModel implements Customer {
    /**
     * @var Address
     */
    protected $address;

    /**
     * @return Address
     */
    public function getAddress()
    {
        if (is_null($this->address)) {
            $value = $this->raw(Customer::FIELD_ADDRESS);
            if (is_null($value)) {
                return $this->mapData(Address::class, null);
            }
            $value = $this->mapData(Address::class, $value);

            $this->address = $value;
        }
        return $this->address;
    }

    /**
     * @param Address $address
     * @return $this
     */
    public function setAddress(Address $address)
    {
        $this->address = $address;

        return $this;
    }

}
