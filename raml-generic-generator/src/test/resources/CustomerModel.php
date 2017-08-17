<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

class CustomerModel extends JsonObject implements Customer {
    /**
     * @var Address
     */
    private $address;

    /**
     * @return Address
     */
    public function getAddress() { return $this->address; }
}
