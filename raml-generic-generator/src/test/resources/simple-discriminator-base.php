<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Ctp\Types;

class PersonModel implements Person {
    const DISCRIMINATOR_VALUE = '';

    public function __construct() {
        $this->kind = static::DISCRIMINATOR_VALUE;
    }

    /**
     * @var string
     */
    private $kind;

    /**
     * @return string
     */
    public function getKind() { return $this->kind; }
}
