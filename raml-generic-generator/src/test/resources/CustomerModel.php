<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObjectModel;
use Test\Base\ResourceClassMap;

class CustomerModel extends JsonObjectModel implements Customer {
    /**
     * @var Address
     */
    private $address;

    /**
     * @return Address
     */
    public function getAddress()
    {
        if (is_null($this->address)) {
            $value = $this->raw(Customer::FIELD_ADDRESS);
            $mappedClass = ResourceClassMap::getMappedClass(Address::class);
            if (is_null($value)) {
                return new $mappedClass([]);
            }
            $this->address = new $mappedClass($value);
        }
        return $this->address;
    }

    /**
     * @param Address $address
     * @return $this
     */
    public function setAddress(Address  $address)
    {
        $this->address = $address;

        return $this;
    }

}
