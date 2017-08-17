<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

class PersonModel extends JsonObject implements Person {
    /**
     * @var string
     */
    private $name;

    /**
     * @return string
     */
    public function getName()
    {
        if (is_null($this->name)) {
            $value = $this->raw('name');
            $this->name = (string)$value;
        }
        return $this->name;
    }


}
