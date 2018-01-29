<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObject;

interface Address extends JsonObject {
    const FIELD_STREET = 'street';

    /**
     * @return string
     */
    public function getStreet();

    /**
     * @param string $street
     * @return $this
     */
    public function setStreet(string $street);

}
